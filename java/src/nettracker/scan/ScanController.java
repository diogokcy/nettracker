package nettracker.scan;

import nettracker.config.UserConfig;
import nettracker.host.Host;
import nettracker.host.HostScanner;
import nettracker.threads.ThreadManager;

import java.util.ArrayList;
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

    public boolean isScanning() {
        return isScanning;
    }

    @Override
    public void onStartScan(ThreadManager threadManager, UserConfig userConfig) {
        if (isScanning) {
            stopCurrentScan();
        }

        this.threadManager = threadManager;
        this.isScanning = true;

        List<Host> hosts = generateHosts(userConfig);

        this.totalTasks = hosts.size();
        this.completedTasks = 0;

        for (Host host : hosts) {
            HostScanner hostScanner = new HostScanner(host, 1000,this);
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


    private List<Host> generateHosts(UserConfig config) {
        List<Host> hosts = new ArrayList<>();

        int start = config.isFullSubnet() ? 0 : config.getStartAddress();
        int end = config.isFullSubnet() ? 255 : config.getEndAddress();

        String network = config.getNetworkAddress();

        for (int i = start; i <= end; i++) {
            String ip = network + "." + i;
            hosts.add(new Host(ip));
        }

        return hosts;
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
