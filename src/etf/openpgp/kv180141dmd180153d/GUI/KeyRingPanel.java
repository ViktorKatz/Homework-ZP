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
	
	protected static String[] columnNames;
	
	protected JScrollPane scrollPane;
	protected JTable table;
	protected Vector<Key> keys;
	
	protected abstract void setColumnNames();

	KeyRingPanel(List<String[]> keysData) {
		setColumnNames();
		this.setLayout(null);
		
		String[][] tableData = keysData.toArray(new String[0][]);
		table = new JTable(tableData, columnNames);

		scrollPane = new JScrollPane(table);
		scrollPane.setSize(780, 600);

		this.add(scrollPane);
	}
	
	public void refreshData() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.fireTableDataChanged();
	}
	
	public int[] getSelectedKeys() {
		return table.getSelectedRows();
	}
}
