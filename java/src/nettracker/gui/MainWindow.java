package nettracker.gui;

import nettracker.config.UserConfig;
import nettracker.gui.panels.*;
import nettracker.gui.controller.MainWindowController;
import nettracker.host.Host;
import nettracker.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainWindow extends JFrame {
    private NetworkConfigPanel networkConfigPanel;
    private ResultsTablePanel resultsTablePanel;
    private StatusPanel statusPanel;

    private MainWindowController controller;

    public MainWindow() {
        super("Net Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Utils.WIDTH, Utils.HEIGHT);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        networkConfigPanel = new NetworkConfigPanel();
        resultsTablePanel = new ResultsTablePanel();
        statusPanel = new StatusPanel();

        add(networkConfigPanel, BorderLayout.NORTH);
        add(resultsTablePanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        controller = new MainWindowController(this);

        initActionListeners();

        setVisible(true);
    }

    private void initActionListeners() {
        JButton[] startButtons = statusPanel.getStartButtons();
        JButton[] stopButtons = statusPanel.getStopButtons();

        for (int i = 0; i < startButtons.length; i++) {
            final int index = i;
            startButtons[i].addActionListener(e -> controller.startScan(index));
            stopButtons[i].addActionListener(e -> controller.stopScan());
        }
    }

    public NetworkConfigPanel getNetworkConfigPanel() {
        return networkConfigPanel;
    }

    public void clearTable() {
        SwingUtilities.invokeLater(() -> resultsTablePanel.clear());
    }


    public void addHostToTable(Host host) {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel tableModel = resultsTablePanel.getTableModel();
            tableModel.addRow(new Object[]{host.getAddress(), host.isConnected() ? "Ativo" : "---"});
        });
    }

    public void updateProgressBar(int value) {
        SwingUtilities.invokeLater(() -> statusPanel.getProgressBar().setValue(value));
    }

    public void resetProgressBar(int max) {
        SwingUtilities.invokeLater(() -> {
            JProgressBar progressBar = statusPanel.getProgressBar();
            progressBar.setMinimum(0);
            progressBar.setMaximum(max);
            progressBar.setValue(0);
        });
    }

    public void setStatus(String statusText) {
        SwingUtilities.invokeLater(() -> statusPanel.getStatusLabel().setText(statusText));
    }

    public void updateTimer(int seconds) {
        SwingUtilities.invokeLater(() -> statusPanel.getTimerLabel().setText("Tempo decorrido: " + seconds + " seg."));
    }

    public void updateTimerWithStop(int seconds) {
        SwingUtilities.invokeLater(() -> statusPanel.getTimerLabel().setText("PARADO APÓS: " + seconds + " seg."));
    }

    public void enableStopButton(int index) {
        SwingUtilities.invokeLater(() -> {
            JButton[] stopButtons = statusPanel.getStopButtons();
            for (int i = 0; i < stopButtons.length; i++) {
                stopButtons[i].setEnabled(i == index);
            }
        });
    }

    public void disableStopButtons() {
        SwingUtilities.invokeLater(() -> {
            JButton[] stopButtons = statusPanel.getStopButtons();
            for (JButton stopButton : stopButtons) {
                stopButton.setEnabled(false);
            }
        });
    }

    public void showError(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE));
    }

    public UserConfig getUserConfig() {
        return networkConfigPanel.getUserConfig();
    }
}
