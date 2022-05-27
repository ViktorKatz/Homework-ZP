package etf.openpgp.kv180141dmd180153d.GUI;

import java.util.Vector;
import java.util.stream.Collectors;

import etf.openpgp.kv180141dmd180153d.Key;

public class PublicKeyRingPanel extends KeyRingPanel {

	private static final long serialVersionUID = 5633565043952864790L;

	@Override
	protected void setColumnNames() {
		columnNames = new String[] { "Timestamp", "Key ID", "Public Key", "User ID" };
	}

	PublicKeyRingPanel(Vector<Key> keys) {
		super(keys.stream().map(k -> {
			return new String[] {
					k.getTimestampString(),
					k.getKeyID(),
					k.getPublicKey(),
					k.getEmail()
			};
		}).collect(Collectors.toList()));
	}
}
