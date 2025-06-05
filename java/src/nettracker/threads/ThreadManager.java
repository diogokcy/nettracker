package nettracker.threads;

public interface ThreadManager {
    void submitTask(Runnable task);
    void shutdown();
    void shutdownNow();
}
