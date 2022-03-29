import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable, ActionListener {
    public int timeLimit = 0;
    public int maxProcessingTime = 0;
    public int minProcessingTime = 0;
    public int maxArrivalTime = 0;
    public int minArrivalTime = 0;
    public int numberOfServers = 0;
    public int numberOfClients = 0;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;

    private final SimulationFrame frame;

    private List <Task> generatedTasks;

    private static SimulationManager gen;

    private static int maxSize;
    private static int PeakHour;

    public SimulationManager()
    {
        frame = new SimulationFrame();
        frame.Start.addActionListener(this);
        frame.Validate.addActionListener(this);
        frame.Start.setEnabled(false);
    }

    private void generateNRandomTasks()
    {
        Random random = new Random();
        for (int i = 0; i < numberOfClients; i++)
        {
            int processingTime = random.nextInt(maxProcessingTime + 1 - minProcessingTime) + minProcessingTime;
            int arrivalTime = random.nextInt(maxArrivalTime + 1 - minArrivalTime) + minArrivalTime;
            Task t = new Task(i + 1, arrivalTime, processingTime);
            generatedTasks.add(t);
        }
        Collections.sort(generatedTasks);
    }

    @Override
    public void run()
    {
        try
        {
            int currentTime = 0;
            FileWriter myWriter = new FileWriter("log.txt", true);
            myWriter.write("================================================\nSimulation\n");
            ArrayList <Task> Remove = new ArrayList <>();
            while (currentTime < timeLimit)
            {
                for (Task t : generatedTasks) {
                    if (t.getArrivalTime() == currentTime)
                    {
                        scheduler.dispatchTask(t);
                        Remove.add(t);
                    }
                }
                for (int i = 0; i < numberOfServers; i++) {
                    scheduler.servers.get(i).setWaitingPeriod();
                }
                for (Task task : Remove) {
                    generatedTasks.remove(task);
                }
                ComputePeekHour(currentTime);

                String log = "Time " + currentTime + "\nWaiting clients: ";
                if (generatedTasks.isEmpty()) {
                    log += "empty\n";
                }
                else {
                    log += generatedTasks.toString() + "\n";
                }

                for (int i = 0; i < numberOfServers; i++) {
                    log += "Queue " + (i + 1) + ": " + scheduler.getServers().get(i).toString() + "\n";
                }
                log += "\n";
                frame.Log.setText(frame.Log.getText() + log);   //step by step
                //frame.Log.setText(log);
                myWriter.write(log);
                currentTime++;
                Thread.sleep(1000);
            }
            double avgWait = scheduler.servers.get(0).getTotalWait() / (double)scheduler.servers.get(0).getNrClientsServed();
            frame.AvgWaitTime.setText(String.valueOf(avgWait));
            double avgService = scheduler.servers.get(0).getTotalService() / (double)scheduler.servers.get(0).getNrClientsServed();
            frame.AvgServiceTime.setText(String.valueOf(avgService));
            frame.PHour.setText(String.valueOf(PeakHour));
            String finalResults = "Average Waiting Time: " + avgWait + "\nAverage Service Time: " + avgService + "\nPeak Hour: " + PeakHour + "\n\n";
            myWriter.write(finalResults);
            myWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ComputePeekHour(int currentTime)
    {
        int k = 0;
        for (int i = 0; i < numberOfServers; i++)
        {
            Task[] nrTask = scheduler.servers.get(i).getTasks();
            if (nrTask.length != 0) {
                k += nrTask.length;
            }
        }
        if (k > maxSize)
        {
            maxSize = k;
            PeakHour = currentTime;
        }
    }

    public static void main(String[] args) {
        gen = new SimulationManager();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == frame.Validate)
        {
            frame.AvgWaitTime.setText("");
            frame.AvgServiceTime.setText("");
            frame.PHour.setText("");

            numberOfClients = Integer.parseInt(frame.N.getText());
            numberOfServers = Integer.parseInt(frame.Q.getText());
            timeLimit = Integer.parseInt(frame.tMAXSimulation.getText());
            minArrivalTime = Integer.parseInt(frame.tMINArrival.getText());
            maxArrivalTime = Integer.parseInt(frame.tMAXArrival.getText());
            minProcessingTime = Integer.parseInt(frame.tMINService.getText());
            maxProcessingTime = Integer.parseInt(frame.tMAXService.getText());
            if (numberOfClients <= 0 || numberOfServers <= 0 || timeLimit <= 0 || minArrivalTime <= 0 || maxArrivalTime <= 0 || minProcessingTime <= 0 || maxProcessingTime <= 0) {
                frame.Log.setText("INVALID SIMULATION VALUES! PLEASE INSERT OTHER VALUES");
            }
            else if (minArrivalTime > maxArrivalTime || minProcessingTime > maxProcessingTime) {
                frame.Log.setText("INVALID SIMULATION VALUES! PLEASE INSERT OTHER VALUES");
            }
            else
            {
                frame.Log.setText("SIMULATION VALUES ARE VALID. START THE SIMULATION\n");
                frame.Start.setEnabled(true);
            }
        }
        if (e.getSource() == frame.Start)
        {
            maxSize = 0;
            PeakHour = 0;
            scheduler = new Scheduler(numberOfServers, timeLimit);
            scheduler.changeStrategy(selectionPolicy);

            generatedTasks = new ArrayList<>(numberOfClients);
            generateNRandomTasks();
            Thread t = new Thread(gen);
            t.start();
        }
    }
}
