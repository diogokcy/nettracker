package nettracker.config;

import java.util.ArrayList;
import java.util.List;
import nettracker.host.Host;
import nettracker.utils.Utils;

public class UserConfig {
    private final String networkAddress;
    private final boolean fullSubnet;
    private final int startAddress;
    private final int endAddress;
    private final int threadNumber;
    private int version;

    public UserConfig(String networkBase, boolean fullSubnet, int start, int end, int threadNumber, boolean useCoreNumber) {
        this.networkAddress = networkBase;
        this.fullSubnet = fullSubnet;
        this.startAddress = start;
        this.endAddress = end;
        this.threadNumber = useCoreNumber ? Utils.NUMBER_OF_CORES : threadNumber;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    public boolean isFullSubnet() {
        return fullSubnet;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public int getEndAddress() {
        return endAddress;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public int getTotalHosts() {
        return (fullSubnet) ? 255 : (endAddress - startAddress);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Host> createHostList() {
        List<Host> hosts = new ArrayList<>();
        int rangeStart = fullSubnet ? 0 : startAddress;
        int rangeEnd = fullSubnet ? 255 : endAddress;

        for (int i = rangeStart; i <= rangeEnd; i++) {
            String ip = networkAddress + "." + i;
            hosts.add(new Host(ip));
        }
        return hosts;
    }
}
