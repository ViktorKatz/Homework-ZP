package etf.openpgp.kv180141dmd180153d;

import javax.swing.*;

public class GUI_Main {

	private final static String windowTitle = "ZP domaci";
	private final static int windowX = 800;
	private final static int windowY = 800;
	private final static int horizontalBreak = 600;

	private JFrame windowFrame = new JFrame();
	private JTabbedPane tabsPane = new JTabbedPane();
	private JPanel privateKeyRingTab = new PrivateKeyRingPanel();
	private JPanel publicKeyRingTab = new PublicKeyRingPanel();
	private JPanel buttonPanel = new JPanel();

	public GUI_Main() {
		tabsPane.setBounds(0, 0, windowX, horizontalBreak);
		tabsPane.add("Private Key Ring", privateKeyRingTab);
		tabsPane.add("Public Key Ring", publicKeyRingTab);

		JButton newKeyPairButton = new JButton("Create new key pair");
		newKeyPairButton.addActionListener(e -> new GUI_Main());
		buttonPanel.add(newKeyPairButton);
		buttonPanel.setBounds(0, horizontalBreak, windowX, windowY - horizontalBreak);

		windowFrame.add(tabsPane);
		windowFrame.add(buttonPanel);

		windowFrame.setSize(windowX, windowY);
		windowFrame.setTitle(windowTitle);
		windowFrame.setLayout(null);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI_Main();
	}

}
