package etf.openpgp.kv180141dmd180153d.GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.stream.Collectors;

import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;

import etf.openpgp.kv180141dmd180153d.Key;
import etf.openpgp.kv180141dmd180153d.RingCollections;

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
	
	public List<String[]> getTableDataFromRingCollection() {
		PGPSecretKeyRingCollection privRings = RingCollections.getPrivRings();
		PGPSecretKeyRing ring;
		List<String[]> res = new ArrayList<String[]>();
		Iterator<PGPSecretKeyRing> iter = privRings.getKeyRings();
		while (iter.hasNext()) {
			ring = iter.next();
			PGPSecretKey key = ring.getSecretKey();
			String keyId = Long.toString(key.getKeyID());
			String email = key.getUserIDs().next();
			String ts = Long.toString(key.getPublicKey().getValidSeconds());
			
			res.add(new String[]{ts, keyId, "", "", email});
		}
		return res;
	}
}
