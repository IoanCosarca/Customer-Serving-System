import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public synchronized void addTask(List<Server> servers, Task t)
    {
        int MinServer = 0;
        int MinWait = servers.get(0).getWaitingPeriod();
        for (int i = 1; i < servers.size(); i++) {
            if (servers.get(i).getWaitingPeriod() < MinWait)
            {
                MinWait = servers.get(i).getWaitingPeriod();
                MinServer = i;
            }
        }
        servers.get(MinServer).addTask(t);
    }
}
