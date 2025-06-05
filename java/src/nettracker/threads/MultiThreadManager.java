package nettracker.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadManager implements ThreadManager {
    private final ExecutorService executor;

    public MultiThreadManager(int numThreads) {
        if (numThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be greater than 0.");
        }

        executor = Executors.newFixedThreadPool(numThreads);
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
