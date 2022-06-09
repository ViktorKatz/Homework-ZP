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
		sendMessageButton.addActionListener(e -> new WindowEncryptMessage(this, RingCollections.getPubVec(), RingCollections.getPrivVec()));
		buttonPanel.add(sendMessageButton);

		JButton receiveMessageButton = new JButton("Receive message");
		receiveMessageButton.addActionListener(e -> receiveMessage());
		buttonPanel.add(receiveMessageButton);

		JButton importKeyButton = new JButton("Import key");
		importKeyButton.addActionListener(e -> importKey());
		buttonPanel.add(importKeyButton);

		JButton exportKeyButton = new JButton("Export key");
		exportKeyButton.addActionListener(e -> exportKey());
		buttonPanel.add(exportKeyButton);
		
		JButton exportKeyButton2 = new JButton("Export public from private key");
		exportKeyButton2.addActionListener(e -> exportPublicKey());
		buttonPanel.add(exportKeyButton2);
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
		long[] ids = selectedTab.getSelectedKeys();

		RingCollections.remove(ids, selectedTab == privateKeyRingTab);
		
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

	private void importKey() {
		JFileChooser keyFileChooser = new JFileChooser(".");
		if (JFileChooser.APPROVE_OPTION == keyFileChooser.showOpenDialog(this)) {
			File selectedFile = keyFileChooser.getSelectedFile();
			boolean res = true;
			if (tabsPane.getSelectedComponent() == privateKeyRingTab)
				res = RingCollections.importPriv(selectedFile);
			else res = RingCollections.importPub(selectedFile);
			if (!res) {
				JOptionPane.showMessageDialog(this, "An error occured", "Cannot import key", JOptionPane.WARNING_MESSAGE);
				return;
			}
			saveDataToDisk();
			refreshTables();
		}
	}

	private void exportKey() {
		boolean priv = tabsPane.getSelectedComponent() == privateKeyRingTab;
		
		if (priv) {
			if (privateKeyRingTab.getSelectedKeys().length != 1) {
				JOptionPane.showMessageDialog(this, "Please select one key", "Cannot export key", JOptionPane.WARNING_MESSAGE);
				return;
			}
			long id = privateKeyRingTab.getSelectedKeys()[0];
			JFileChooser keyFileChooser = new JFileChooser(".");
			if (JFileChooser.APPROVE_OPTION == keyFileChooser.showSaveDialog(this)) {
				File selectedFile = keyFileChooser.getSelectedFile();

				RingCollections.exportPriv(id, selectedFile);

				refreshTables();
			}
		}
		else {
			if (publicKeyRingTab.getSelectedKeys().length != 1) {
				JOptionPane.showMessageDialog(this, "Please select one key", "Cannot export key", JOptionPane.WARNING_MESSAGE);
				return;
			}
			long id = publicKeyRingTab.getSelectedKeys()[0];
			JFileChooser keyFileChooser = new JFileChooser(".");
			if (JFileChooser.APPROVE_OPTION == keyFileChooser.showSaveDialog(this)) {
				File selectedFile = keyFileChooser.getSelectedFile();

				RingCollections.exportPub(id, selectedFile);

				refreshTables();
			}
		}


	}
	
	private void exportPublicKey() {
		System.out.println("export myPub");
		if (tabsPane.getSelectedComponent() != privateKeyRingTab
				|| privateKeyRingTab.getSelectedKeys().length != 1) {
			JOptionPane.showMessageDialog(this, "Please select one private key", "Cannot export key", JOptionPane.WARNING_MESSAGE);
			return;
		}

		long selectedPrivateKey = privateKeyRingTab.getSelectedKeys()[0];

		JFileChooser keyFileChooser = new JFileChooser(".");
		if (JFileChooser.APPROVE_OPTION == keyFileChooser.showSaveDialog(this)) {
			File selectedFile = keyFileChooser.getSelectedFile();

			RingCollections.exportPubFromPriv(selectedPrivateKey, selectedFile);

			refreshTables();
		}
	}

	public static void main(String[] args) {
		getInstance();
	}

}
