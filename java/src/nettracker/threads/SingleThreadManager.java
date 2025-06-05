package nettracker.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadManager implements ThreadManager {
    private final ExecutorService executor;

    public SingleThreadManager() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void submitTask(Runnable task) {
        executor.submit(task);
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public void shutdownNow() {
        executor.shutdownNow();
    }
}
