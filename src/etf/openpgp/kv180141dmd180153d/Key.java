package etf.openpgp.kv180141dmd180153d;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import etf.openpgp.kv180141dmd180153d.algorithms.IAsymmetricKeyAlgorithm;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.util.io.Streams;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.oer.its.ieee1609dot2.basetypes.SymmAlgorithm;

public class Key implements Serializable {
	
	private static final long serialVersionUID = -7062109191445608706L;
	
	public static void newKey(String email, String password, IAsymmetricKeyAlgorithm algorithm) {
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
	        	SymmetricKeyAlgorithmTags.AES_128
	        });
	        signSubGen.setPreferredHashAlgorithms(false, new int[] {
	        	HashAlgorithmTags.SHA256,
	            HashAlgorithmTags.SHA1,
	            HashAlgorithmTags.SHA384,
	            HashAlgorithmTags.SHA512,
	            HashAlgorithmTags.SHA224,
	        });
	        signSubGen.setPreferredCompressionAlgorithms(false, new int[] {
	        	CompressionAlgorithmTags.ZIP
	        });
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
	                new BcPGPContentSignerBuilder(signKeyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1),
	                encryptor
	        );
	        
	        keyRingGen.addSubKey(encyptKeyPair, encryptSubGen.generate(), null);
	        
	        PGPPublicKeyRing pubKeyRing = keyRingGen.generatePublicKeyRing();
	        PGPSecretKeyRing privKeyRing = keyRingGen.generateSecretKeyRing();
	        
	        RingCollections.addPrivKey(privKeyRing, pubKeyRing);
	        
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static long getDecryptKeyId(File filein) {
        PGPObjectFactory objFact;
		try {
			objFact = new PGPObjectFactory(PGPUtil.getDecoderStream(new FileInputStream(filein)), new BcKeyFingerprintCalculator());
	        Object obj = objFact.nextObject();
	        if (obj instanceof PGPEncryptedDataList) {
	    		Iterator<PGPEncryptedData> iter = ((PGPEncryptedDataList) obj).getEncryptedDataObjects();
	    		PGPPublicKeyEncryptedData data = (PGPPublicKeyEncryptedData) iter.next();
	    		return data.getKeyID();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static boolean sendMessage(File infile, File outfile, int privateKeyId, int[] publicKeyIds, boolean compressZip,
			boolean convertToRadix, int symmAlg, String password) {
		
		PGPSecretKeyRing privKey = null;
		Vector<PGPPublicKeyRing> pubKeys = new Vector<PGPPublicKeyRing>();
		if (privateKeyId > 0) {
			privKey = RingCollections.getPrivRing(privateKeyId - 1);
		}
		if (privKey != null) System.out.println("private: " + privKey.getPublicKey().getUserIDs().next());
		for (int id : publicKeyIds)
			pubKeys.add(RingCollections.getPubRing(id));
		
		Provider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        OutputStream out, out2, out3, out4;
		try {
			out = new FileOutputStream(outfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

        if (convertToRadix) {
            out = new ArmoredOutputStream(out);
        }

        BcPGPDataEncryptorBuilder encBuilder;
        PGPEncryptedDataGenerator encDataGen = null;

        if (pubKeys.size() > 0) {
        	encBuilder = new BcPGPDataEncryptorBuilder(symmAlg == 1 ? PGPEncryptedData.TRIPLE_DES : PGPEncryptedData.CAST5);
        	encBuilder.setWithIntegrityPacket(true);
        	encBuilder.setSecureRandom(new SecureRandom());
        	encDataGen = new PGPEncryptedDataGenerator(encBuilder);
        	Iterator<PGPPublicKey> iter = pubKeys.get(0).getPublicKeys();
        	PGPPublicKey pk = null;
        	while(iter.hasNext()) {
        		pk = iter.next();
        		if(pk.isEncryptionKey())
        			break;
        	}
        	if (pk.getUserIDs().hasNext()) System.out.println("public: " + pk.getUserIDs().next());
            encDataGen.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(pk));
            try {
				out2 = encDataGen.open(out, new byte[65536]);
			} catch (IOException | PGPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
        }
        else out2 = out;
        
        PGPCompressedDataGenerator compDataGen = new PGPCompressedDataGenerator(compressZip ? PGPCompressedData.ZIP : PGPCompressedData.UNCOMPRESSED);
        try {
        	out3 = compDataGen.open(out2, new byte [65536]);
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        
        PGPSignatureGenerator signGen = null;
        if (privKey != null) {
        	PGPSecretKey sk = privKey.getSecretKey();
        	PBESecretKeyDecryptor decryptor = new BcPBESecretKeyDecryptorBuilder(
                    new BcPGPDigestCalculatorProvider()).build(password.toCharArray());
            try {
				PGPPrivateKey prk = sk.extractPrivateKey(decryptor);
				PGPContentSignerBuilder signBuilder = new BcPGPContentSignerBuilder(sk.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1);
				signGen = new PGPSignatureGenerator(signBuilder);
	            signGen.init(PGPSignature.BINARY_DOCUMENT, prk);
	            
	            PGPSignatureSubpacketGenerator subGen = new PGPSignatureSubpacketGenerator();
                subGen.setSignerUserID(false, privKey.getPublicKey().getUserIDs().next());
                signGen.setHashedSubpackets(subGen.generate());
                signGen.generateOnePassVersion(false).encode(out3);
				
			} catch (PGPException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
        }
        try {
        	PGPLiteralDataGenerator litDataGen = new PGPLiteralDataGenerator();
			out4 = litDataGen.open(out3, PGPLiteralData.BINARY, infile.getAbsolutePath(), new Date(), new byte[65536]);
			
			FileInputStream in = new FileInputStream(infile);
	        byte[] buffer = new byte[65536];
	        int length;
	        while ((length = in.read(buffer)) > 0) {
	            out4.write(buffer, 0, length);
	            if (privKey != null)
	                signGen.update(buffer, 0, length);
	        }
	        in.close();
	        litDataGen.close();
	        if (privKey != null)
	            signGen.generate().encode(out3);
	        compDataGen.close();
            if (encDataGen != null)
            	encDataGen.close();
            out.close();
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public static String receiveMessage(File filein, PGPSecretKeyRing privKey, String password) {
		PGPObjectFactory objFact;
		try {
			objFact = new PGPObjectFactory(PGPUtil.getDecoderStream(new FileInputStream(filein)), new BcKeyFingerprintCalculator());
	        Object obj = objFact.nextObject();
	        if (obj instanceof PGPEncryptedDataList) {
	    		Iterator<PGPEncryptedData> iter = ((PGPEncryptedDataList) obj).getEncryptedDataObjects();
	    		PGPPublicKeyEncryptedData data = (PGPPublicKeyEncryptedData) iter.next();
	    		
	    		PBESecretKeyDecryptor decryptor = new BcPBESecretKeyDecryptorBuilder(
	                    new BcPGPDigestCalculatorProvider()).build(password.toCharArray());
	    		
	    		PGPPrivateKey pk = privKey.getSecretKey().extractPrivateKey(decryptor);
	    		InputStream is = ((PGPPublicKeyEncryptedData) data).getDataStream(new BcPublicKeyDataDecryptorFactory(pk));
	            objFact = new PGPObjectFactory(is, new BcKeyFingerprintCalculator());
	        }
	        
	        PGPOnePassSignatureList opSigList = null;
	        PGPSignatureList sigList = null;
	        PGPCompressedData compData;

	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        while ((obj = objFact.nextObject()) != null) {
	            if (obj instanceof PGPLiteralData) {

	                Streams.pipeAll(((PGPLiteralData) obj).getInputStream(), out);
	            }
	            if (obj instanceof PGPCompressedData) {
	                compData = (PGPCompressedData) obj;
	                objFact = new PGPObjectFactory(compData.getDataStream(), new BcKeyFingerprintCalculator());
	                obj = objFact.nextObject();
	            }
	            if (obj instanceof PGPOnePassSignatureList) {
	                opSigList = (PGPOnePassSignatureList) obj;
	            }
	            if (obj instanceof PGPSignatureList) {
	                sigList = (PGPSignatureList) obj;
	            }
	        }
	        
	        out.close();
	        OutputStream os = new FileOutputStream(filein.getAbsoluteFile() + "-dec");
	        os.write(out.toByteArray());
	        os.close();

	        byte[] output = out.toByteArray();

	        if (opSigList == null || sigList == null) {
	            return "Unsigned";
	        }
	        
	        String ret = "";
	        for (int i = 0; i < opSigList.size(); ++i) {
	        	PGPOnePassSignature opSig = opSigList.get(0);
	        	PGPPublicKey pubKey = RingCollections.getPubRings().getPublicKey(opSig.getKeyID());
	        }
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Error Occured";
	}
}
