package etf.openpgp.kv180141dmd180153d.algorithms;

public class RSA extends KeyAlgotithm implements IAsymmetricKeyAlgorithm {

	private final int[] allowedKeySizes = { 1024, 2048, 4096 };
	
	private int keySize;

	public RSA(int keySize) {
		boolean usedAllowedKeySize = false;
		for (int allowedKeySize : allowedKeySizes) {
			if (keySize == allowedKeySize) {
				usedAllowedKeySize = true;
				break;
			}
		}
		if (!usedAllowedKeySize) {
			throw new IllegalArgumentException("The key size is not allowed.");
		}

		this.keySize = keySize;
	}

	@Override
	public String getUserFriendlyName() {
		return String.format("RSA with %d-bit key", keySize);
	}

	public int getKeySize() {
		return keySize;
	}
}
