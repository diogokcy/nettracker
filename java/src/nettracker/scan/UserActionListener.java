package nettracker.scan;

import nettracker.config.UserConfig;
import nettracker.threads.ThreadManager;

public interface UserActionListener {
    void onStartScan(ThreadManager threadManager, UserConfig userConfig);
    void onStopScan();
}
