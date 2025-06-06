package nettracker.scan;

import nettracker.config.UserConfig;
import nettracker.host.Host;
import nettracker.host.HostScanner;
import nettracker.threads.*;
import nettracker.utils.Utils;

import java.util.List;

public class ScanController implements ScanListener, UserActionListener {
    private final ScanListener listener;
    private ThreadManager threadManager;
    private boolean isScanning = false;

    private int totalTasks;
    private int completedTasks;

    public ScanController(ScanListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStartScan(UserConfig userConfig) {
        if (isScanning) {
            stopCurrentScan();
        }

        threadManager = switch (userConfig.getVersion()) {
            case 0 -> new NoThreadManager();
            case 1 -> new SingleThreadManager();
            case 2 -> new MultiThreadManager(userConfig.getThreadNumber());
            case 3 -> new DynamicThreadManager();
            default -> throw new IllegalArgumentException("Versão inválida");
        };

        this.isScanning = true;

        List<Host> hosts = userConfig.createHostList();

        this.totalTasks = hosts.size();
        this.completedTasks = 0;

        for (Host host : hosts) {
            HostScanner hostScanner = new HostScanner(host, Utils.TIMEOUT,this);
            threadManager.submitTask(hostScanner);
        }

        threadManager.shutdown();
    }

    @Override
    public synchronized void onStopScan() {
        stopCurrentScan();
        listener.onScanFinished();
    }

    private synchronized void stopCurrentScan() {
        if (isScanning) {
            isScanning = false;
            threadManager.shutdownNow();
            threadManager = null;
        }
    }

    @Override
    public synchronized void onHostScanned(Host host) {
        if (isScanning) {
            listener.onHostScanned(host);
            completedTasks++;
            if (completedTasks >= totalTasks) {
                onScanFinished();
            }
        }
    }

    @Override
    public synchronized void onScanFinished() {
        if (isScanning) {
            isScanning = false;
            listener.onScanFinished();
        }
    }

}
