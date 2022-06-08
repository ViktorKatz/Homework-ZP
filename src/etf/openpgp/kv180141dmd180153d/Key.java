package etf.openpgp.kv180141dmd180153d;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import etf.openpgp.kv180141dmd180153d.algorithms.IAsymmetricKeyAlgorithm;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.oer.its.ieee1609dot2.basetypes.SymmAlgorithm;

public class Key implements Serializable {
	
	private static final long serialVersionUID = -7062109191445608706L;
	
	private Date timestamp;
	private String keyName;
	private String publicKey;
	private String email;
	private String encryptedPrivateKey; // samo za private key.

	Key(String filepath) { // Import one key from file
		throw new NotImplementedException(); // TODO @gavantee: treba nam ucitavanje iz fajla, "ako nam neko posalje svoj javni kljuc" uuuuu. Promeni parametar na bas fajl, ako zelis.
	}

	public Key(String keyName, String email, String password, IAsymmetricKeyAlgorithm algorithm) {
		int keySize = algorithm.getKeySize();
		RSAKeyPairGenerator keyPairGen = new RSAKeyPairGenerator();
		keyPairGen.init(new RSAKeyGenerationParameters(BigInteger.valueOf(0x10001), new SecureRandom(), keySize, 12));
        try {
			PGPKeyPair signKeyPair = new BcPGPKeyPair(PGPPublicKey.RSA_SIGN, keyPairGen.generateKeyPair(), new Date());
	        PGPKeyPair encyptKeyPair = new BcPGPKeyPair(PGPPublicKey.RSA_ENCRYPT, keyPairGen.generateKeyPair(), new Date());
	        PGPSignatureSubpacketGenerator signSubGen = new PGPSignatureSubpacketGenerator();
	        signSubGen.setKeyFlags(false, KeyFlags.SIGN_DATA | KeyFlags.CERTIFY_OTHER);
	        signSubGen.setPreferredSymmetricAlgorithms(false, new int[] {
	        		SymmetricKeyAlgorithmTags.AES_256,
	        		SymmetricKeyAlgorithmTags.AES_192,
	        		SymmetricKeyAlgorithmTags.AES_128, });
	        signSubGen.setPreferredHashAlgorithms(false, new int[] {
	        		HashAlgorithmTags.SHA256,
	                HashAlgorithmTags.SHA1,
	                HashAlgorithmTags.SHA384,
	                HashAlgorithmTags.SHA512,
	                HashAlgorithmTags.SHA224, });
	        signSubGen.setPreferredCompressionAlgorithms(false, new int[] { CompressionAlgorithmTags.ZIP });
	        
	        PGPSignatureSubpacketGenerator encryptSubGen = new PGPSignatureSubpacketGenerator();
	        encryptSubGen.setKeyFlags(false, KeyFlags.ENCRYPT_COMMS | KeyFlags.ENCRYPT_STORAGE);
	        
	        PBESecretKeyEncryptor encryptor = (new BcPBESecretKeyEncryptorBuilder(PGPEncryptedData.AES_256)).build(password.toCharArray());
	        PGPKeyRingGenerator keyRingGen = new PGPKeyRingGenerator(
	                PGPPublicKey.RSA_SIGN,
	                signKeyPair,
	                email,
	                new BcPGPDigestCalculatorProvider().get(HashAlgorithmTags.SHA1),
	                signSubGen.generate(),
	                null,
	                new BcPGPContentSignerBuilder(PGPPublicKey.RSA_SIGN, HashAlgorithmTags.SHA256),
	                encryptor
	        );
	        keyRingGen.addSubKey(encyptKeyPair, encryptSubGen.generate(), null);
	        
	        PGPPublicKeyRing pubKeyRing = keyRingGen.generatePublicKeyRing();
	        PGPSecretKeyRing privKeyRing = keyRingGen.generateSecretKeyRing();
	        
	        RingCollections.addPrivKey(privKeyRing, pubKeyRing);
	        System.out.println("RSA GEN");
	        
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void exportToFile(String path) {
		throw new NotImplementedException(); // TODO @gavantee: treba nam i eksportovanje jednog kljuca u fajl, ako cemo mi nekom da posaljemo svoj javni
	}
	
	public String encryptMessage(String message) {
		throw new NotImplementedException(); // TODO @gavantee: Slobodno promeni tipove, kako god ti odgovara, mozda najbolje da prihvatas fajl parametar 
	}
	
	public String decryptMessage(String encryptedMessage) {
		throw new NotImplementedException(); // TODO @gavantee: Slobodno promeni tipove, kako god ti odgovara, mozda najbolje da prihvatas fajl parametar
	}
	
	public boolean checkPassword(String password) {
		return password.equals("123"); // TODO @gavantee check password
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getTimestampString() {
		return timestamp.toString();
	}

	public String getKeyName() {
		return keyName;
	}

	public String getKeyID() {
		return "TODO"; // TODO @gavantee: prvih par slova public keya, idk, videces
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getEmail() {
		return email;
	}

	public String getEncriptedPrivateKey() {
		return encryptedPrivateKey;
	}
	
	@Override
	public String toString() {
		return keyName + " (" + email + ")";
	}

	// Samo za moje potrebe testiranja. Posle cu izbrisati kad napravis nacin da se konstruise kljuc.
	private Key(Date timestamp, String keyName, String publicKey, String email, String encriptedPrivateKey) {
		this.timestamp = timestamp;
		this.keyName = keyName;
		this.publicKey = publicKey;
		this.email = email;
		this.encryptedPrivateKey = encriptedPrivateKey;
	}

	// Samo za moje potrebe testiranja. Posle cu izbrisati kad napravis nacin da se konstruise kljuc.
	public static Key getDummytKeyObject() {
		return new Key(
				new Date(System.currentTimeMillis()),
				"Ime test kljuca",
				"13uirh239rh3298rh3298rh3wfw23f4j46j64h56h56h4g24g355h23g42",
				"testmail@test.test",
				"094jg3490gk390gk3409gk3490gk32fwf23f24f34f3g35g35gh46h57je");
	}
}
