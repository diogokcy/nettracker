package nettracker.gui.controller;

import nettracker.config.UserConfig;
import nettracker.host.Host;
import nettracker.scan.ScanController;
import nettracker.scan.ScanListener;
import nettracker.threads.*;
import nettracker.utils.ScanTimer;
import nettracker.gui.MainWindow;

public class MainWindowController implements ScanListener {

    private final MainWindow mainWindow;
    private final ScanController scanController;
    private final ScanTimer scanTimer;

    private boolean stopHit = false;
    private int scannedHosts = 0;

    public MainWindowController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.scanController = new ScanController(this);
        this.scanTimer = new ScanTimer();
    }

    public void startScan(int version) {
        stopHit = false;

        UserConfig config = mainWindow.getNetworkConfigPanel().getUserConfig();

        if (!validateUserConfig(config, version)) {
            mainWindow.showError("Configuração inválida!");
            return;
        }

        mainWindow.clearTable();
        mainWindow.resetProgressBar(config.getTotalHosts());
        mainWindow.setStatus("Status: EXECUTANDO");
        mainWindow.enableStopButton(version);

        scannedHosts = 0;

        scanTimer.start(mainWindow::updateTimer);

        ThreadManager threadManager = switch (version) {
            case 0 -> new NoThreadManager();
            case 1 -> new SingleThreadManager();
            case 2 -> new MultiThreadManager(config.getThreadNumber());
            case 3 -> new DynamicThreadManager();
            default -> throw new IllegalArgumentException("Versão inválida");
        };

        scanController.onStartScan(threadManager, config);
    }

    public void stopScan() {
        stopHit = true;
        scanController.onStopScan();
        scanTimer.stop();
        mainWindow.setStatus("Status: PARADO");
        mainWindow.updateTimerWithStop(scanTimer.getElapsedSeconds());
        mainWindow.disableStopButtons();
    }

    @Override
    public void onHostScanned(Host host) {
        scannedHosts++;
        mainWindow.addHostToTable(host);
        mainWindow.updateProgressBar(scannedHosts);
    }

    @Override
    public void onScanFinished() {
        if (!stopHit) {
            mainWindow.setStatus("Status: CONCLUÍDO");
            mainWindow.updateProgressBar(scannedHosts);
            scanTimer.stop();
            mainWindow.updateTimer(scanTimer.getElapsedSeconds());
        }
        mainWindow.disableStopButtons();
    }

    private boolean validateUserConfig(UserConfig config, int version) {
        if (config.getNetworkAddress() == null || config.getNetworkAddress().isEmpty()) return false;
        if (config.getStartAddress() > config.getEndAddress()) return false;
        if (config.getStartAddress() < 0 || config.getStartAddress() > 255) return false;
        if (config.getEndAddress() < 0 || config.getEndAddress() > 255) return false;

        if (version != 0) {
            int numThreads = config.getThreadNumber();
            return numThreads > 0 && numThreads <= 128;
        }

        return true;
    }
}