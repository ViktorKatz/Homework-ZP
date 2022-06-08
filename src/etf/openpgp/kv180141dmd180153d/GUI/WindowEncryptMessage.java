package etf.openpgp.kv180141dmd180153d.GUI;

import java.awt.GridLayout;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;

import etf.openpgp.kv180141dmd180153d.Key;

public class WindowEncryptMessage extends JDialog {

	private static final long serialVersionUID = 3081872127865290034L;

	private final static String windowTitle = "Encrypt message";
	private final static int windowX = 500;
	private final static int windowY = 500;

	private final JButton chooseInputFileButton = new JButton("Choose input file");
	private final JTextField inputFilePeek = new JTextField("Select file to encrypt");
	private File selectedFile = null;

	private JComboBox<PGPSecretKeyRing> signKeysComboBox;
	private JList<PGPPublicKeyRing> encryptKeysList;

	private JCheckBox compressCheckBox = new JCheckBox("Compress message");
	private JCheckBox convertToRadixCheckBox = new JCheckBox("Convert to radix-64");

	private final JButton sendToFileButton = new JButton("Send (export to file)");

	public WindowEncryptMessage(JFrame mainWindow, Vector<PGPPublicKeyRing> pubRings, Vector<PGPSecretKeyRing> privRings) {
		super(mainWindow, true);
		this.setLayout(new GridLayout(5, 2));

		chooseInputFileButton.addActionListener(e -> {
			JFileChooser inputFileChooser = new JFileChooser(".");
			if (JFileChooser.APPROVE_OPTION == inputFileChooser.showOpenDialog(this)) {
				selectedFile = inputFileChooser.getSelectedFile();
				inputFilePeek.setText(selectedFile.getName());
			}
		});
		inputFilePeek.setEditable(false);

		encryptKeysList = new JList<PGPPublicKeyRing>(pubRings);
		JScrollPane encryptKeysPane = new JScrollPane(encryptKeysList);

		Vector<PGPSecretKeyRing> availableSignKeysWithNone = new Vector<PGPSecretKeyRing>(privRings);
		availableSignKeysWithNone.add(0, null);
		signKeysComboBox = new JComboBox<PGPSecretKeyRing>(privRings);

		sendToFileButton.addActionListener(e -> {
			File selectedFile = this.selectedFile;
			PGPSecretKeyRing privateKey = (PGPSecretKeyRing) signKeysComboBox.getSelectedItem();
			List<PGPPublicKeyRing> publicKeys = encryptKeysList.getSelectedValuesList();
			boolean compressZip = compressCheckBox.isSelected();
			boolean convertToRadix = convertToRadixCheckBox.isSelected();
			
			if(null == selectedFile) {
				JOptionPane.showMessageDialog(this, "Please select a file to encrypt", "Cannot encrypt nothing", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(null != privateKey) {
				String password = JOptionPane.showInputDialog(this, "Password for " + signKeysComboBox.getSelectedItem());
				//if(!privateKey.checkPassword(password)) {
					//JOptionPane.showMessageDialog(this, "Wrong password!");
					//return;
				//}
			}
			
			// TODO @gavantee: uradi nesto sa ovim varijablama koje sam ostavio na pocetku funkcije.
			
			JFileChooser outputFileChooser = new JFileChooser(".");
			if (JFileChooser.APPROVE_OPTION == outputFileChooser.showSaveDialog(this)) {
				//throw new NotImplementedException(); // TODO @gavantee: Enkriptovanu poruku sacuvaj u fajl
			}
		});

		this.add(inputFilePeek);
		this.add(chooseInputFileButton);
		this.add(new JLabel("Encryption keys:"));
		this.add(encryptKeysPane);
		this.add(new JLabel("Sign key:"));
		this.add(signKeysComboBox);
		this.add(compressCheckBox);
		this.add(convertToRadixCheckBox);
		this.add(new JLabel(""));
		this.add(sendToFileButton);

		this.setSize(windowX, windowY);
		this.setResizable(false);
		this.setTitle(windowTitle);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
