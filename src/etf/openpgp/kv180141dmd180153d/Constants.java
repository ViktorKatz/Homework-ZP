package etf.openpgp.kv180141dmd180153d;

import java.util.ArrayList;
import java.util.List;

final class Constants {
	final static List<IAsymmetricKeyAlgorithm> supportedAsymetricAlgorithms = new ArrayList<IAsymmetricKeyAlgorithm>();
	final static List<ISymmetricKeyAlgorithm> supportedSymetricAlgorithms = new ArrayList<ISymmetricKeyAlgorithm>();
	
	static {
		supportedAsymetricAlgorithms.add(new RSA(1024));
		supportedAsymetricAlgorithms.add(new RSA(2048));
		supportedAsymetricAlgorithms.add(new RSA(4096));
		
		supportedSymetricAlgorithms.add(new ThreeDESwithEDE());
		supportedSymetricAlgorithms.add(new CAST5());
	}
}
