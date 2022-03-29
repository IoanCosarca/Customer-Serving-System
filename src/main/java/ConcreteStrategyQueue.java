import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public synchronized void addTask(List<Server> servers, Task t)
    {
        int MinQueue = 0;
        int MinSize = servers.get(0).getTasks().length;
        System.out.println(MinSize);
        for (int i = 1; i < servers.size(); i++) {
            if (servers.get(i).getTasks().length < MinSize)
            {
                MinSize = servers.get(i).getTasks().length;
                MinQueue = i;
            }
        }
        servers.get(MinQueue).addTask(t);
    }
}
