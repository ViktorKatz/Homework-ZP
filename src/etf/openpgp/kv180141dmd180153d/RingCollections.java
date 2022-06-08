package etf.openpgp.kv180141dmd180153d;

import java.io.IOException;
import java.util.ArrayList;

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
			pubRings = new PGPPublicKeyRingCollection(new ArrayList<PGPPublicKeyRing>());
			privRings = new PGPSecretKeyRingCollection(new ArrayList<PGPSecretKeyRing>());
			myPubRings = new PGPPublicKeyRingCollection(new ArrayList<PGPPublicKeyRing>());
			
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addPubKey(PGPPublicKeyRing pubRing) {
		pubRings = PGPPublicKeyRingCollection.addPublicKeyRing(pubRings, pubRing);
	}
	
	public static void addPrivKey(PGPSecretKeyRing privRing, PGPPublicKeyRing pubRing) {
		privRings = PGPSecretKeyRingCollection.addSecretKeyRing(privRings, privRing);
		myPubRings = PGPPublicKeyRingCollection.addPublicKeyRing(myPubRings, pubRing);
	}
	
	
	public static PGPSecretKeyRingCollection getPrivRings() {
		return privRings;
	}
	
	public static PGPPublicKeyRingCollection getPubRings() {
		return pubRings;
	}
	
	public static PGPPublicKeyRingCollection getMyPubRings() {
		return myPubRings;
	}
}
