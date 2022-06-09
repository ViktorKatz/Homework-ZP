package etf.openpgp.kv180141dmd180153d;

import java.util.Vector;

import etf.openpgp.kv180141dmd180153d.algorithms.CAST5;
import etf.openpgp.kv180141dmd180153d.algorithms.IAsymmetricKeyAlgorithm;
import etf.openpgp.kv180141dmd180153d.algorithms.ISymmetricKeyAlgorithm;
import etf.openpgp.kv180141dmd180153d.algorithms.RSA;
import etf.openpgp.kv180141dmd180153d.algorithms.ThreeDESwithEDE;

public final class Constants {
	public static final Vector<IAsymmetricKeyAlgorithm> supportedAsymetricAlgorithms = new Vector<IAsymmetricKeyAlgorithm>();
	public static final Vector<ISymmetricKeyAlgorithm> supportedSymetricAlgorithms = new Vector<ISymmetricKeyAlgorithm>();
	
	
	static {
		supportedAsymetricAlgorithms.add(new RSA(1024));
		supportedAsymetricAlgorithms.add(new RSA(2048));
		supportedAsymetricAlgorithms.add(new RSA(4096));
		
		supportedSymetricAlgorithms.add(new ThreeDESwithEDE());
		supportedSymetricAlgorithms.add(new CAST5());
	}
}
