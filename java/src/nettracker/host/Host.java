package nettracker.host;

public class Host {
    private final String address;
    private boolean connected;

    public Host(String address) {
        this.address = address;
        this.connected = false;
    }

    public String getAddress() {
        return address;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
