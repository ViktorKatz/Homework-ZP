package etf.openpgp.kv180141dmd180153d;

import java.io.IOException;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;

public class RingCollections {
	private static PGPPublicKeyRingCollection pubRings;
	private static PGPSecretKeyRingCollection privRings;
	private static PGPPublicKeyRingCollection myPubRings;
	
	public static void init() {
		try {
			pubRings = new PGPPublicKeyRingCollection(null);
			privRings = new PGPSecretKeyRingCollection(null);
			myPubRings = new PGPPublicKeyRingCollection(null);
			
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addPubKey(PGPPublicKeyRing pubRing) {
		PGPPublicKeyRingCollection.addPublicKeyRing(pubRings, pubRing);
	}
	
	public static void addPrivKey(PGPSecretKeyRing privRing, PGPPublicKeyRing pubRing) {
		PGPSecretKeyRingCollection.addSecretKeyRing(privRings, privRing);
		PGPPublicKeyRingCollection.addPublicKeyRing(myPubRings, pubRing);
	}
}
