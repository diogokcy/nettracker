package nettracker.host;

import nettracker.scan.ScanListener;
import nettracker.utils.Utils;

import java.io.IOException;
import java.net.InetAddress;

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
        boolean status = ping(host.getAddress(), timeout);
        host.setConnected(status);
        listener.onHostScanned(host);
    }

    private static boolean ping(String ip, int timeout) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }
}
