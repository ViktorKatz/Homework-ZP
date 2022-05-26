package etf.openpgp.kv180141dmd180153d.GUI;

import javax.swing.*;

public class WindowMain extends JFrame {
	private static final long serialVersionUID = 1L;

	private static WindowMain instance = null;

	public static WindowMain getInstance() {
		if (instance == null)
			instance = new WindowMain();
		return instance;
	}

	private final static String windowTitle = "ZP homework, Viktor i Dragan";
	private final static int windowX = 800;
	private final static int windowY = 800;
	private final static int horizontalBreak = 600;

	private String[][] privateKeys = {
			{ "Test Timestamp", "Test Key ID", "Test Public Key", "Test Encripted Private Key", "Test User ID" },
			{ "T2est Timestamp", "T2est Key ID", "Tes2t Public Key", "Te2st Encripted Private Key", "Te2st User ID" },
	};

	private String[][] publicKeys = {
			{ "TPUBLest Timestamp", "Test Key ID", "Test Public Key", "Test User ID" },
			{ "T2PUBest Timestamp", "T2est Key ID", "Tes2t Public Key", "Te2st User ID" },
	};

	private JTabbedPane tabsPane = new JTabbedPane();
	private PrivateKeyRingPanel privateKeyRingTab = new PrivateKeyRingPanel(privateKeys);
	private PublicKeyRingPanel publicKeyRingTab = new PublicKeyRingPanel(publicKeys);
	private JPanel buttonPanel = new JPanel();

	private WindowMain() {
		tabsPane.setBounds(0, 0, windowX, horizontalBreak);
		tabsPane.add("Private Key Ring", privateKeyRingTab);
		tabsPane.add("Public Key Ring", publicKeyRingTab);

		JButton newKeyPairButton = new JButton("Create new key pair");
		newKeyPairButton.addActionListener(e -> new WindowNewKeyPair());
		buttonPanel.add(newKeyPairButton);
		buttonPanel.setBounds(0, horizontalBreak, windowX, windowY - horizontalBreak);

		this.add(tabsPane);
		this.add(buttonPanel);

		this.setSize(windowX, windowY);
		this.setTitle(windowTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void refreshData() {
		privateKeyRingTab.refreshData();
		publicKeyRingTab.refreshData();
	}

	public static void main(String[] args) {
		getInstance();
	}

}
