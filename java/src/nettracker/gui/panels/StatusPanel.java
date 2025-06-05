package nettracker.gui.panels;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {

    private JLabel status;
    private JProgressBar progressBar;
    private JLabel timer;

    private JButton[] startButtons;
    private JButton[] stopButtons;

    public StatusPanel() {
        setLayout(new BorderLayout());

        JPanel versionPanel = initVersionPanel();
        JPanel progressPanel = initProgressPanel();

        add(versionPanel, BorderLayout.CENTER);
        add(progressPanel, BorderLayout.SOUTH);
    }

    private JPanel initVersionPanel() {
        JPanel versionPanel = new JPanel(new GridLayout(1, 4));

        startButtons = new JButton[4];
        stopButtons = new JButton[4];

        String[] titles = {
                "Versão 0: sem threads",
                "Versão 1: single thread",
                "Versão 2: multi threads",
                "Versão 3: multi threads dinâmicas"
        };

        for (int i = 0; i < 4; i++) {
            JPanel vPanel = new JPanel(new FlowLayout());
            vPanel.setBorder(BorderFactory.createTitledBorder(titles[i]));

            startButtons[i] = new JButton("Iniciar");
            stopButtons[i] = new JButton("Parar");
            stopButtons[i].setEnabled(false);

            vPanel.add(startButtons[i]);
            vPanel.add(stopButtons[i]);

            versionPanel.add(vPanel);
        }

        return versionPanel;
    }

    private JPanel initProgressPanel() {
        JPanel progressPanel = new JPanel(new BorderLayout());

        status = new JLabel("Status: PARADO");
        progressBar = new JProgressBar();
        progressBar.setForeground(Color.GREEN);
        timer = new JLabel("Concluído em: ? seg. Timer fechado", SwingConstants.RIGHT);

        progressPanel.add(status, BorderLayout.WEST);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        progressPanel.add(timer, BorderLayout.EAST);

        return progressPanel;
    }

    public JButton[] getStartButtons() {
        return startButtons;
    }

    public JButton[] getStopButtons() {
        return stopButtons;
    }

    public JLabel getStatusLabel() {
        return status;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getTimerLabel() {
        return timer;
    }
}