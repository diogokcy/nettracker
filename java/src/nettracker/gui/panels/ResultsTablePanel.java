package nettracker.gui.panels;

import nettracker.host.Host;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultsTablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public ResultsTablePanel() {
        setLayout(new BorderLayout());

        String[] columns = {"IP", "Estado de ativação"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addHost(Host host) {
        tableModel.addRow(new Object[]{host.getAddress(), host.isConnected() ? "Ativo" : "---"});
    }

    public void clear() {
        tableModel.setRowCount(0);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }
}