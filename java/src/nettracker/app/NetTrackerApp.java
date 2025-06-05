package nettracker.app;

import nettracker.gui.MainWindow;

import javax.swing.*;

public class NetTrackerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
