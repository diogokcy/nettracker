package nettracker.gui.panels;

import nettracker.config.UserConfig;
import nettracker.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class NetworkConfigPanel extends JPanel {

    private JTextField networkAddress;
    private JCheckBox scanFullSubnet;
    private JSpinner startAddress;
    private JSpinner endAddress;
    private JCheckBox useCores;

    public NetworkConfigPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(initNetworkPanel());
        add(initAddressPanel());
        add(initThreadPanel());
    }

    private JPanel initNetworkPanel() {
        JPanel networkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        networkPanel.add(new JLabel("Classe C da rede a varrer:"));
        networkAddress = new JTextField(10);
        networkPanel.add(networkAddress);
        networkPanel.add(new JLabel("Formato: 255.255.255"));
        return networkPanel;
    }

    private JPanel initAddressPanel() {
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addressPanel.add(new JLabel("Range de IPs a varrer:"));

        scanFullSubnet = new JCheckBox("Varrer toda sub-rede");
        addressPanel.add(scanFullSubnet);

        addressPanel.add(new JLabel("ou Início:"));
        startAddress = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        addressPanel.add(startAddress);

        addressPanel.add(new JLabel("Fim:"));
        endAddress = new JSpinner(new SpinnerNumberModel(255, 0, 255, 1));
        addressPanel.add(endAddress);

        return addressPanel;
    }

    private JPanel initThreadPanel() {
        JPanel threadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        threadPanel.add(new JLabel("Número de threads a usar:"));

        useCores = new JCheckBox("número de cores do PC " + "(" + Utils.NUMBER_OF_CORES + ")");
        threadPanel.add(useCores);

        threadPanel.add(new JLabel("ou Nº threads:"));
        JSpinner numThreads = new JSpinner(new SpinnerNumberModel(128, 0, 128, 1));
        threadPanel.add(numThreads);

        return threadPanel;
    }

    public UserConfig getUserConfig() {
        String network = networkAddress.getText().trim();
        boolean fullSubnet = scanFullSubnet.isSelected();
        int start = (Integer) startAddress.getValue();
        int end = (Integer) endAddress.getValue();
        boolean cores = useCores.isSelected();
        return new UserConfig(network, fullSubnet, start, end, cores);
    }
}