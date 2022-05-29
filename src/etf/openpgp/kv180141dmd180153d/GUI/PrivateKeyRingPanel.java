package etf.openpgp.kv180141dmd180153d.GUI;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import etf.openpgp.kv180141dmd180153d.Key;

public class PrivateKeyRingPanel extends KeyRingPanel {

	private static final long serialVersionUID = -4876447046177035242L;

	@Override
	protected void setColumnNames() {
		columnNames = new String[] { "Timestamp", "Key ID", "Public Key", "Encripted Private Key", "User ID" };
	}

	PrivateKeyRingPanel(Vector<Key> keys) {
		super(keys);
	}

	@Override
	protected List<String[]> getTableDataFromKeyVector(Vector<Key> keys) {
		return keys.stream().map(k -> {
			return new String[] {
					k.getTimestampString(),
					k.getKeyID(),
					k.getPublicKey(),
					k.getEncriptedPrivateKey(),
					k.getEmail()
			};
		}).collect(Collectors.toList());
	}
}
