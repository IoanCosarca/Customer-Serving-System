import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    protected List <Server> servers;
    private final int maxNoServers;
    private final int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer)
    {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<>();
        for (int i = 0; i < maxNoServers; i++)
        {
            Server s = new Server(maxTasksPerServer);
            servers.add(s);
            Thread t = new Thread(s);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy)
    {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t) {
        strategy.addTask(servers, t);
    }

    public List <Server> getServers() {
        return this.servers;
    }

}
