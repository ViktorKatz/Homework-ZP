package etf.openpgp.kv180141dmd180153d.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PrivateKeyRingPanel extends JPanel {

	private static final long serialVersionUID = -4876447046177035242L;
	private static final String[] columnNames = { "Timestamp", "Key ID", "Public Key", "Encripted Private Key", "User ID" };

	private JScrollPane scrollPane;
	private JTable table;

	PrivateKeyRingPanel(String[][] tableData) {
		this.setLayout(null);
		table = new JTable(tableData, columnNames);

		scrollPane = new JScrollPane(table);
		scrollPane.setSize(780, 600);

		this.add(scrollPane);
	}
	
	public void refreshData() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.fireTableDataChanged();
	}
}
