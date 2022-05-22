package etf.openpgp.kv180141dmd180153d;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PublicKeyRingPanel extends JPanel {
	private static final long serialVersionUID = 5633565043952864790L;
	private static final String[] columnNames = { "Timestamp", "Key ID", "Public Key", "User ID"};
	private String[][] tableData = {
			{ "TPUBLest Timestamp", "Test Key ID", "Test Public Key", "Test User ID" },
			{ "T2PUBest Timestamp", "T2est Key ID", "Tes2t Public Key", "Te2st User ID" },
	};

	private JScrollPane scrollPane;
	private JTable table;

	PublicKeyRingPanel() {
		this.setLayout(null);
		table = new JTable(tableData, columnNames);

		scrollPane = new JScrollPane(table);
		scrollPane.setSize(780, 600);

		this.add(scrollPane);
	}
}
