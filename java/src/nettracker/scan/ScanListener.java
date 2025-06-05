package nettracker.scan;

import nettracker.host.Host;

public interface ScanListener {
    void onHostScanned(Host host);
    void onScanFinished();
}
