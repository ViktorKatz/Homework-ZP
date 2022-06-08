package etf.openpgp.kv180141dmd180153d;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;

public class RingCollections {
	private static PGPSecretKeyRingCollection privRings;
	private static PGPPublicKeyRingCollection pubRings;
	private static PGPPublicKeyRingCollection myPubRings;
	
	public static void init() {
		try {
            privRings = new PGPSecretKeyRingCollection(
            	PGPUtil.getDecoderStream(new FileInputStream("myKeys")),
            	new BcKeyFingerprintCalculator()
            );
            myPubRings = new PGPPublicKeyRingCollection(
            	PGPUtil.getDecoderStream(new FileInputStream("myKeys.pub")),
            	new BcKeyFingerprintCalculator()
            );
            pubRings = new PGPPublicKeyRingCollection(
                PGPUtil.getDecoderStream(new FileInputStream("publicKeys.pub")),
                new BcKeyFingerprintCalculator()
            );
			
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
