package etf.openpgp.kv180141dmd180153d;

import javax.swing.*;

public class PrivateKeyRingPanel extends JPanel {

	private static final long serialVersionUID = -4876447046177035242L;
	private static final String[] columnNames = { "Timestamp", "Key ID", "Public Key", "Encripted Private Key", "User ID" };
	private String[][] tableData = {
			{ "Test Timestamp", "Test Key ID", "Test Public Key", "Test Encripted Private Key", "Test User ID" },
			{ "T2est Timestamp", "T2est Key ID", "Tes2t Public Key", "Te2st Encripted Private Key", "Te2st User ID" },
	};

	private JScrollPane scrollPane;
	private JTable table;

	PrivateKeyRingPanel() {
		this.setLayout(null);
		table = new JTable(tableData, columnNames);

		scrollPane = new JScrollPane(table);
		scrollPane.setSize(780, 600);

		this.add(scrollPane);
	}
}
