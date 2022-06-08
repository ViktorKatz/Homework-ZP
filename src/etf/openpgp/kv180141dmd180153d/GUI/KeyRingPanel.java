package etf.openpgp.kv180141dmd180153d.GUI;

import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import etf.openpgp.kv180141dmd180153d.Key;

public abstract class KeyRingPanel extends JPanel {

	private static final long serialVersionUID = -5802339836769941121L;

	protected String[] columnNames;

	protected JScrollPane scrollPane;
	protected JTable table;

	protected abstract void setColumnNames();
	protected abstract List<String[]> getTableDataFromRingCollection();

	KeyRingPanel() {
		setColumnNames();

		table = new JTable();
		refreshData();

		scrollPane = new JScrollPane(table);
		scrollPane.setSize(780, 600);

		this.setLayout(null);
		this.add(scrollPane);
	}

	public void refreshData() {
		List<String[]> keysData = getTableDataFromRingCollection();
		String[][] tableData = keysData.toArray(new String[0][]);
		table.setModel(new DefaultTableModel(tableData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}

	public void refreshTable() {
		refreshData();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.fireTableDataChanged();
	}

	public long[] getSelectedKeys() {
		int[] rows = table.getSelectedRows();
		long[] ids = new long[rows.length];
		for (int i = 0; i < ids.length; ++i) {
			ids[i] = Long.parseUnsignedLong((String) table.getValueAt(rows[i], 1), 16);
		}
		return ids;
	}
	
    public static String toHex(byte[] bytes)
    {
        StringBuilder result = new StringBuilder();
  
        for (byte i : bytes) {
            int decimal = (int)i & 0XFF;
            String hex = Integer.toHexString(decimal);
  
            if (hex.length() % 2 == 1) {
                hex = "0" + hex;
            }
  
            result.append(hex);
        }
        return result.toString();
    }
}
