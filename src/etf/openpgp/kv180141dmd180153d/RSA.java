package etf.openpgp.kv180141dmd180153d;

public class RSA implements IAsymmetricKeyAlgorithm {

	private final int[] allowedKeySizes = { 1024, 2048, 4096 };
	
	private int keySize;

	RSA(int keySize) {
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

}
