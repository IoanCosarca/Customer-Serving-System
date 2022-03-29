import javax.swing.*;
import java.awt.*;

public class SimulationFrame {
    private final JLabel NumberClients;
    protected JTextField N;
    private final JLabel NumberQueue;
    protected JTextField Q;
    private final JLabel SimulationInterval;
    protected JTextField tMAXSimulation;
    private final JLabel MinimumArrivalTime;
    protected JTextField tMINArrival;
    private final JLabel MaximumArrivalTime;
    protected JTextField tMAXArrival;
    private final JLabel MinimumServiceTime;
    protected JTextField tMINService;
    private final JLabel MaximumServiceTime;
    protected JTextField tMAXService;

    private final JLabel AverageWaitingTime;
    protected JTextField AvgWaitTime;
    private final JLabel AverageServiceTime;
    protected JTextField AvgServiceTime;
    private final JLabel PeakHour;
    protected JTextField PHour;

    protected JButton Validate;
    protected JButton Start;

    protected JTextArea Log;
    private final JScrollPane slider;

    SimulationFrame()
    {
        JFrame content = new JFrame("Queue management system");
        content.setSize(750, 820);
        content.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLocationRelativeTo(null);
        content.setResizable(false);
        content.setTitle("Queue management system");
        content.setLayout(null);

        NumberClients       = new JLabel("Number of Clients");
        N                   = new JTextField("0");
        NumberQueue         = new JLabel("Number of Queues");
        Q                   = new JTextField("0");
        SimulationInterval  = new JLabel("Simulation Interval");
        tMAXSimulation      = new JTextField("0");
        MinimumArrivalTime  = new JLabel("Minimum Arrival Time");
        tMINArrival         = new JTextField("0");
        MaximumArrivalTime  = new JLabel("Maximum Arrival Time");
        tMAXArrival         = new JTextField("0");
        MinimumServiceTime  = new JLabel("Minimum Service Time");
        tMINService         = new JTextField("0");
        MaximumServiceTime  = new JLabel("Maximum Service Time");
        tMAXService         = new JTextField("0");
        AverageWaitingTime  = new JLabel("Average Waiting Time");
        AvgWaitTime         = new JTextField();
        AverageServiceTime  = new JLabel("Average Service Time");
        AvgServiceTime      = new JTextField();
        PeakHour            = new JLabel("Peak Hour");
        PHour               = new JTextField();
        Validate            = new JButton("VALIDATE");
        Start               = new JButton("START");
        Log                 = new JTextArea();
        slider              = new JScrollPane(Log);

        JPanel input = new JPanel();
        input.setLayout(new GridLayout(7, 2));
        input.setBounds(10,5,720,230);
        content.add(input);
        input.add(NumberClients);
        input.add(N);
        input.add(NumberQueue);
        input.add(Q);
        input.add(SimulationInterval);
        input.add(tMAXSimulation);
        input.add(MinimumArrivalTime);
        input.add(tMINArrival);
        input.add(MaximumArrivalTime);
        input.add(tMAXArrival);
        input.add(MinimumServiceTime);
        input.add(tMINService);
        input.add(MaximumServiceTime);
        input.add(tMAXService);

        Validate.setBounds(10,240,120,40);
        Validate.setFocusable(false);
        content.add(Validate);
        Start.setBounds(10,285,120,40);
        Start.setFocusable(false);
        content.add(Start);

        JPanel results = new JPanel();
        results.setLayout(new GridLayout(2,3));
        results.setBounds(150,220,550,100);
        content.add(results);
        AvgWaitTime.setEditable(false);
        AvgServiceTime.setEditable(false);
        PHour.setEditable(false);
        results.add(AverageWaitingTime);
        results.add(AverageServiceTime);
        results.add(PeakHour);
        results.add(AvgWaitTime);
        results.add(AvgServiceTime);
        results.add(PHour);

        Log.setEditable(false);
        slider.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        slider.setBounds(10,330,720,440);
        content.add(slider);

        content.setVisible(true);
    }
}
