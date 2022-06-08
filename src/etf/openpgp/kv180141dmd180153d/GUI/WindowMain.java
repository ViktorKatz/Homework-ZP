package etf.openpgp.kv180141dmd180153d.GUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.*;

import etf.openpgp.kv180141dmd180153d.Constants;
import etf.openpgp.kv180141dmd180153d.Key;
import etf.openpgp.kv180141dmd180153d.RingCollections;
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

	private JTabbedPane tabsPane = new JTabbedPane();
	private PrivateKeyRingPanel privateKeyRingTab; // Init after loading data from disk
	private PublicKeyRingPanel publicKeyRingTab; // Init after loading data from disk
	private JPanel buttonPanel = new JPanel();

	private WindowMain() {
		tabsPane.setBounds(0, 0, windowX, horizontalBreak);

		loadDataFromDisk();
		privateKeyRingTab = new PrivateKeyRingPanel();
		publicKeyRingTab = new PublicKeyRingPanel();
		tabsPane.add("Private Key Ring", privateKeyRingTab);
		tabsPane.add("Public Key Ring", publicKeyRingTab);

		addButtons();

		this.add(tabsPane);
		this.add(buttonPanel);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				saveDataToDisk();
			};
		});
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
		newKeyPairButton.addActionListener(e -> {
			new WindowNewKeyPair(this);
			refreshTables();
		});
		buttonPanel.add(newKeyPairButton);
		buttonPanel.setBounds(0, horizontalBreak, windowX, windowY - horizontalBreak);

		JButton deleteSelectedKeysButton = new JButton("Delete selected keys");
		deleteSelectedKeysButton.addActionListener(e -> deleteSelectedKeys());
		buttonPanel.add(deleteSelectedKeysButton);

		JButton sendMessageButton = new JButton("Send message");
		sendMessageButton.addActionListener(e -> new WindowEncryptMessage(this, publicKeys, privateKeys));
		buttonPanel.add(sendMessageButton);

		JButton receiveMessageButton = new JButton("Receive message");
		receiveMessageButton.addActionListener(e -> receiveMessage());
		buttonPanel.add(receiveMessageButton);

		JButton importKeyButton = new JButton("Import foreign private key");
		importKeyButton.addActionListener(e -> importPublicKey());
		buttonPanel.add(importKeyButton);

		JButton exportKeyButton = new JButton("Export public key from private key");
		exportKeyButton.addActionListener(e -> exportPrivateKey());
		buttonPanel.add(exportKeyButton);
	}
	
	public void addKeyFromOutside(Key key, boolean isPrivate) { // Used when other windows add keys (new key creation, import etc.)
		Vector<Key> targetKeySet = isPrivate? privateKeys : publicKeys;
		targetKeySet.add(key);
		
		saveDataToDisk();
		refreshTables();
	}

	@SuppressWarnings("unchecked")
	public void loadDataFromDisk() {
		RingCollections.init();
	}

	public void saveDataToDisk() {
        try {
            BufferedOutputStream priv = new BufferedOutputStream(new FileOutputStream("myKeys"));
			RingCollections.getPrivRings().encode(priv);
	        priv.close();

	        BufferedOutputStream myPub = new BufferedOutputStream(new FileOutputStream("myKeys.pub"));
	        RingCollections.getMyPubRings().encode(myPub);
	        myPub.close();
	        
	        BufferedOutputStream pub = new BufferedOutputStream(new FileOutputStream("publicKeys.pub"));
	        RingCollections.getPubRings().encode(pub);
	        pub.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refreshTables() {
		privateKeyRingTab.refreshTable();
		publicKeyRingTab.refreshTable();
	}

	private void deleteSelectedKeys() {
		KeyRingPanel selectedTab = (KeyRingPanel) tabsPane.getSelectedComponent();
		int[] selectedKeys = selectedTab.getSelectedKeys();

		Vector<Key> keySetForRemoval = selectedTab == privateKeyRingTab ? privateKeys : publicKeys;

		int alreadyRemoved = 0;
		for (int sk : selectedKeys) {
			int removal_index = sk - alreadyRemoved; // Shiftuju se, pa moram ovu primitivu da radim. Vektorima stvarno treba removeIndexes(int[]) metoda...
			keySetForRemoval.remove(removal_index);
			
			alreadyRemoved++;
		}

		saveDataToDisk(); // Flush data
		refreshTables();
	}

	private void receiveMessage() {
		JFileChooser messageFileChooser = new JFileChooser(".");
		if (JFileChooser.APPROVE_OPTION == messageFileChooser.showOpenDialog(this)) {
			File selectedFile = messageFileChooser.getSelectedFile();

			String password = JOptionPane.showInputDialog(this, "Please enter password (or leave empty): ");
			// TODO @gavantee: Imas fajl, imas password, sacuvaj fajl koristeci 
			// JFileChooser decodedMsgFileChooser = new JFileChooser(".");
			// decodedMsgFileChooser.showSaveDialog(this)

			refreshTables();
		}
	}

	private void importPublicKey() {
		JFileChooser keyFileChooser = new JFileChooser(".");
		if (JFileChooser.APPROVE_OPTION == keyFileChooser.showOpenDialog(this)) {
			File selectedFile = keyFileChooser.getSelectedFile();

			// TODO @gavantee: Importuj kljuc kako znas i umes

			saveDataToDisk();
			refreshTables();
			throw new NotImplementedException();
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

			// TODO @gavantee: Pozovi ovde stagod implementiras

			refreshTables();

			throw new NotImplementedException();
		}
	}

	public static void main(String[] args) {
		getInstance();
	}

}
