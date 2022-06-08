package etf.openpgp.kv180141dmd180153d;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;

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
	
	public static void remove(long[] ids, boolean priv) {
		if (priv) {
			for (long id : ids) {
				try {
					privRings = PGPSecretKeyRingCollection.removeSecretKeyRing(privRings, privRings.getSecretKeyRing(id));
					//myPubRings = PGPPublicKeyRingCollection.removePublicKeyRing(myPubRings, myPubRings.getPublicKeyRing(id));
				} catch (PGPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		}
		else {
			for (long id : ids) {
				try {
					//myPubRings = PGPPublicKeyRingCollection.removePublicKeyRing(myPubRings, myPubRings.getPublicKeyRing(id));
					pubRings = PGPPublicKeyRingCollection.removePublicKeyRing(pubRings, pubRings.getPublicKeyRing(id));
				} catch (PGPException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		}
	}

	public static void exportPubFromPriv(long id, File selectedFile) {
        try (ArmoredOutputStream out = new ArmoredOutputStream(
                new FileOutputStream(selectedFile))) {
        	privRings.getSecretKeyRing(id).getPublicKey().encode(out);
        } catch (IOException | PGPException e) {
            e.printStackTrace();
        }
	}
	
	public static void exportPub(long id, File selectedFile) {
        try (ArmoredOutputStream out = new ArmoredOutputStream(
                new FileOutputStream(selectedFile))) {
        	pubRings.getPublicKeyRing(id).getPublicKey().encode(out);
        } catch (IOException | PGPException e) {
            e.printStackTrace();
        }
	}
	
	public static void exportPriv(long id, File selectedFile) {
        try (ArmoredOutputStream out = new ArmoredOutputStream(
                new FileOutputStream(selectedFile))) {
        	//privRings.getSecretKeyRing(id).getSecretKey().extractPrivateKey(new JcePBESecretKeyDecryptorBuilder()
        		//     .setProvider(BouncyCastleProvider.PROVIDER_NAME).build(password.toCharArray())).encode(out);
        	privRings.getSecretKeyRing(id).getSecretKey().encode(out);
        	
        } catch (IOException | PGPException e) {
            e.printStackTrace();
        }
	}
	
	public static boolean importPub(File file) {
		PGPPublicKeyRing pubRing;
		try {
			pubRing = new PGPPublicKeyRing(
				PGPUtil.getDecoderStream(new FileInputStream(file)),
				new BcKeyFingerprintCalculator());
			pubRings = PGPPublicKeyRingCollection.addPublicKeyRing(pubRings, pubRing);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean importPriv(File file) {
		PGPSecretKeyRing privRing;
		try {
			privRing = new PGPSecretKeyRing(
				PGPUtil.getDecoderStream(new FileInputStream(file)),
				new BcKeyFingerprintCalculator());
			privRings = PGPSecretKeyRingCollection.addSecretKeyRing(privRings, privRing);
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
