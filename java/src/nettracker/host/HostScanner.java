package nettracker.host;

import nettracker.scan.ScanListener;
import nettracker.utils.Utils;

public class HostScanner implements Runnable {
    private final Host host;
    private final int timeout;
    private final ScanListener listener;

    public HostScanner(Host host, int timeout, ScanListener scanListener) {
        this.host = host;
        this.timeout = timeout;
        this.listener = scanListener;
    }

    @Override
    public void run() {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        boolean status = Utils.ping(host.getAddress(), timeout);
        host.setConnected(status);
        listener.onHostScanned(host);
    }
}
