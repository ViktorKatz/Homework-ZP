package etf.openpgp.kv180141dmd180153d.GUI;

import java.io.File;
import java.util.Vector;

import javax.swing.*;

import etf.openpgp.kv180141dmd180153d.Key;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

	private Vector<Key> privateKeys = new Vector<Key>();
	private Vector<Key> publicKeys = new Vector<Key>();
	{
		privateKeys.add(Key.getDummytKeyObject());
		privateKeys.add(Key.getDummytKeyObject());
		publicKeys.add(Key.getDummytKeyObject());
		publicKeys.add(Key.getDummytKeyObject());
		publicKeys.add(Key.getDummytKeyObject());
		publicKeys.add(Key.getDummytKeyObject());
	}

	private JTabbedPane tabsPane = new JTabbedPane();
	private PrivateKeyRingPanel privateKeyRingTab = new PrivateKeyRingPanel(privateKeys);
	private PublicKeyRingPanel publicKeyRingTab = new PublicKeyRingPanel(publicKeys);
	private JPanel buttonPanel = new JPanel();

	private WindowMain() {
		tabsPane.setBounds(0, 0, windowX, horizontalBreak);
		tabsPane.add("Private Key Ring", privateKeyRingTab);
		tabsPane.add("Public Key Ring", publicKeyRingTab);

		addButtons();

		this.add(tabsPane);
		this.add(buttonPanel);

		this.setResizable(false);
		this.setSize(windowX, windowY);
		this.setTitle(windowTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void addButtons() {
		JButton newKeyPairButton = new JButton("Create new key pair");
		newKeyPairButton.addActionListener(e -> new WindowNewKeyPair(this));
		buttonPanel.add(newKeyPairButton);
		buttonPanel.setBounds(0, horizontalBreak, windowX, windowY - horizontalBreak);

		JButton deleteSelectedKeysButton = new JButton("Delete selected keys");
		deleteSelectedKeysButton.addActionListener(e -> deleteSelectedKeys());
		buttonPanel.add(deleteSelectedKeysButton);

		JButton encryptMessageButton = new JButton("Send message");
		encryptMessageButton.addActionListener(e -> new WindowEncryptMessage(this, publicKeys, privateKeys));
		buttonPanel.add(encryptMessageButton);

		JButton importKeyButton = new JButton("Import foreign private key");
		importKeyButton.addActionListener(e -> importPublicKey());
		buttonPanel.add(importKeyButton);
		
		JButton exportKeyButton = new JButton("Export public key from private key");
		exportKeyButton.addActionListener(e -> exportPrivateKey());
		buttonPanel.add(exportKeyButton);
	}

	public void loadDataFromDisk() {
		throw new NotImplementedException();// TODO viktor: Pozovi Draganove funkcije kad ih napravi
		// refreshData();
	}

	public void saveDataToDisk() { // Ovo ce se pozivati kad se izvrse neke bitne akcije, tipa pravljenje kljuca, brisanje, itd.
		throw new NotImplementedException();// TODO viktor: Pozovi Draganove funkcije kad ih napravi
	}

	public void refreshTables() {
		privateKeyRingTab.refreshData();
		publicKeyRingTab.refreshData();
	}

	private void deleteSelectedKeys() {
		KeyRingPanel selectedTab = (KeyRingPanel) tabsPane.getSelectedComponent();
		int[] selectedKeys = selectedTab.getSelectedKeys();

		System.out.print("(TODO) Selected indexes of keys for deletion: ");
		for (int sk : selectedKeys)
			System.out.print(" " + sk);
		System.out.println();

		throw new NotImplementedException(); // TODO @viktor: kad dragan uradi kljuceve, izbrisi selektovane
		// saveDataToDisk(); // Flush data
	}

	private void importPublicKey() {
		JFileChooser keyFileChooser = new JFileChooser(".");
		if (JFileChooser.APPROVE_OPTION == keyFileChooser.showOpenDialog(this)) {
			File selectedFile = keyFileChooser.getSelectedFile();

			// TODO @viktor: Nakon sto Dragan doda ucitavanje iz .asc fajla, pozovi to

			refreshTables();
		}
	}
	
	private void exportPrivateKey() {
		if (tabsPane.getSelectedComponent() != privateKeyRingTab
				|| privateKeyRingTab.getSelectedKeys().length != 1) {
			JOptionPane.showMessageDialog(this, "Please select one private key to export", "Cannot export key", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int selectedPrivateKeyIndex = privateKeyRingTab.getSelectedKeys()[0];
		Key selectedPrivateKey = privateKeys.elementAt(selectedPrivateKeyIndex);
		
		JFileChooser keyFileChooser = new JFileChooser(".");
		if (JFileChooser.APPROVE_OPTION == keyFileChooser.showSaveDialog(this)) {
			File selectedFile = keyFileChooser.getSelectedFile();

			// TODO @viktor: Nakon sto Dragan doda exportovanje u .asc fajl, pozovi to

			refreshTables();
		}
	}

	public static void main(String[] args) {
		getInstance();
	}

}
