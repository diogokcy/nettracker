package nettracker.utils;

import java.io.IOException;
import java.net.InetAddress;

public class Utils {
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;

    public final static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    public final static int TIMEOUT = 3000;

    public static boolean ping(String ip, int timeout) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }

}
