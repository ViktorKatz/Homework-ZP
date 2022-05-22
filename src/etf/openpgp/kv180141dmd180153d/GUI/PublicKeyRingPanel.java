package etf.openpgp.kv180141dmd180153d.GUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PublicKeyRingPanel extends JPanel {
	private static final long serialVersionUID = 5633565043952864790L;
	private static final String[] columnNames = { "Timestamp", "Key ID", "Public Key", "User ID" };

	private JScrollPane scrollPane;
	private JTable table;

	PublicKeyRingPanel(String[][] tableData) {
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
