package nettracker.scan;

import nettracker.config.UserConfig;
import nettracker.threads.ThreadManager;

public interface UserActionListener {
    void onStartScan(UserConfig userConfig);
    void onStopScan();
}
