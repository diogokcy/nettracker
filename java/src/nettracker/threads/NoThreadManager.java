package nettracker.threads;

public class NoThreadManager implements ThreadManager {

    private boolean shutdown = false;

    @Override
    public void submitTask(Runnable task) {
        if (!shutdown) {
            task.run();
        }
    }

    @Override
    public void shutdown() {
        shutdown = true;
    }

    @Override
    public void shutdownNow() {
        shutdown = true;
    }
}