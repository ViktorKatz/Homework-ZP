package etf.openpgp.kv180141dmd180153d.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.stream.Collectors;

import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;

import etf.openpgp.kv180141dmd180153d.Key;
import etf.openpgp.kv180141dmd180153d.RingCollections;

public class PublicKeyRingPanel extends KeyRingPanel {

	private static final long serialVersionUID = 5633565043952864790L;

	@Override
	protected void setColumnNames() {
		columnNames = new String[] { "Timestamp", "Key ID", "Public Key", "User ID" };
	}

	PublicKeyRingPanel() {
		super();
	}
	
	public List<String[]> getTableDataFromRingCollection() {
		PGPPublicKeyRingCollection pubRings = RingCollections.getPubRings();
		PGPPublicKeyRing ring = null;
		boolean paired = false;
		List<String[]> res = new ArrayList<String[]>();
		Iterator<PGPPublicKeyRing> iter = pubRings.getKeyRings();
		if (paired && iter.hasNext())
			ring = iter.next();
		else {
			iter = pubRings.getKeyRings();
			paired = false;
			if (iter.hasNext())
				ring = iter.next();
		}
		while (ring != null) {
			PGPPublicKey key = ring.getPublicKey();
			String keyId = Long.toHexString(key.getKeyID());
			String email = key.getUserIDs().next();
			String ts = key.getCreationTime().toString();
			String pk = toHex(key.getFingerprint());
			
			res.add(new String[]{ ts, keyId, pk, email });
			if (iter.hasNext())
				ring = iter.next();
			else if (paired) {
				iter = pubRings.getKeyRings();
				paired = false;
				if (iter.hasNext())
					ring = iter.next();
				else ring = null;
			}
			else ring = null;
		}
		return res;
	}
}
