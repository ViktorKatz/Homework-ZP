package etf.openpgp.kv180141dmd180153d;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import etf.openpgp.kv180141dmd180153d.algorithms.IAsymmetricKeyAlgorithm;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/*
 * OK, ovo je nesto sa cime bi bilo kul da radis, posto stavljam to kao klasu koju ce moje metode da primaju
 * Izmenjaj tipove, dodaj metode, stagod. Posle cu ja getere za ispis na GUIju dodati ako mi nesto zafali.
 * */
// Mozes razdvojiti ovo na podklase private i public key ako zelis

public class Key implements Serializable {
	
	private static final long serialVersionUID = -7062109191445608706L;
	
	private Date timestamp;
	private String keyName;
	private String publicKey;
	private String email;
	private String encriptedPrivateKey; // samo za private key.

	Key(String filepath) { // Import one key from file
		throw new NotImplementedException(); // TODO @gavantee: treba nam ucitavanje iz fajla, "ako nam neko posalje svoj javni kljuc" uuuuu. Promeni parametar na bas fajl, ako zelis.
	}

	Key(String keyName, String email, String password, IAsymmetricKeyAlgorithm algorithm) {
		throw new NotImplementedException(); // TODO @gavantee: treba nam generisanje novog kljuca. Promeni parametre slobodno, samo sam pogadjao sta ti moze zatrebati
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
		return encriptedPrivateKey;
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
		this.encriptedPrivateKey = encriptedPrivateKey;
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
