import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private final BlockingQueue <Task> tasks;
    private final AtomicInteger waitingPeriod;

    private static AtomicInteger TotalWait;
    private static int nrClientsServed;
    private static int TotalService;

    public Server(int maxClients)
    {
        tasks = new ArrayBlockingQueue<>(maxClients);
        waitingPeriod = new AtomicInteger(0);
        TotalWait = new AtomicInteger(0);
        nrClientsServed = 0;
        TotalService = 0;
    }

    public synchronized void addTask(Task newTask)
    {
        try
        {
            tasks.put(newTask);
            waitingPeriod.addAndGet(newTask.getServiceTime());
            TotalWait.addAndGet(waitingPeriod.get());
            nrClientsServed++;
            TotalService += newTask.getServiceTime();
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run()
    {
        while (true) {
            try
            {
                Task nextTask = tasks.peek();
                if (nextTask != null)
                {
                    Thread.sleep(1000L * nextTask.getServiceTime());
                    tasks.take();
                }
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Task[] getTasks()
    {
        int size = tasks.size();
        Task[] listTasks = new Task[size];
        if (size != 0)
        {
            BlockingQueue<Task> taskAux = new ArrayBlockingQueue<>(size);
            taskAux.addAll(tasks);
            for (int i = 0; i < size; i++) {
                listTasks[i] = taskAux.poll();
            }
        }
        return listTasks;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public String toString()
    {
        if (this.tasks.isEmpty()) {
            return "closed";
        }
        return this.tasks.toString();
    }

    public int getTotalWait() {
        return TotalWait.get();
    }

    public int getNrClientsServed() {
        return nrClientsServed;
    }

    public int getTotalService() {
        return TotalService;
    }

    public synchronized void setWaitingPeriod() {
        if (this.waitingPeriod.get() > 0) {
            this.waitingPeriod.decrementAndGet();
        }
        else {
            this.waitingPeriod.addAndGet(0);
        }
    }
}
