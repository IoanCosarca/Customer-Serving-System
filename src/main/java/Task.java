public class Task implements Comparable <Task> {
    private final int TaskID;
    private final int arrivalTime;
    private final int serviceTime;

    public Task(int TaskID, int arrivalTime, int serviceTime)
    {
        this.TaskID = TaskID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int compareTo(Task t) {
        return arrivalTime - t.arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public String toString() {
        return "(" + this.TaskID + ", " + this.arrivalTime + ", " + this.serviceTime + ")";
    }
}
