package etf.openpgp.kv180141dmd180153d.GUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import etf.openpgp.kv180141dmd180153d.Constants;
import etf.openpgp.kv180141dmd180153d.Key;
import etf.openpgp.kv180141dmd180153d.algorithms.IAsymmetricKeyAlgorithm;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WindowNewKeyPair extends JDialog {

	private static final long serialVersionUID = 7954094844062145591L;

	private final static String windowTitle = "Create new key pair";
	private final static int windowX = 400;
	private final static int windowY = 200;

	private static JTextField emailField = new JTextField(); 	// Static to save for next key entry
	private JComboBox<IAsymmetricKeyAlgorithm> algoritmsBox = new JComboBox<IAsymmetricKeyAlgorithm>(Constants.supportedAsymetricAlgorithms);
	private JPasswordField passwordField = new JPasswordField();
	private JButton saveButton = new JButton("Save");

	private boolean isAllInfoCorrectlyEntered() {
		if (!emailField.getText().matches(".+[@].+[.].+"))
			return false;
		if (String.valueOf(passwordField.getPassword()).isEmpty())
			return false;
		return true;
	}

	private void saveKey() {		
		// TODO @gavantee: Imas fieldove, uradi nesto
		
		WindowMain mainWindow = (WindowMain) this.getParent();
		Key.newKey(
			emailField.getText(),
			String.valueOf(passwordField.getPassword()),
			(IAsymmetricKeyAlgorithm) algoritmsBox.getSelectedItem()
		);
		
		this.dispose();
		// throw new NotImplementedException(); // TODO @gavantee: delete this when done
	}

	public WindowNewKeyPair(JFrame mainWindow) {
		super(mainWindow, true);
		this.setLayout(new GridLayout(6, 2));

		this.add(new JLabel("E-mail"));
		this.add(emailField);
		this.add(new JLabel("Algorithm"));
		this.add(algoritmsBox);
		this.add(new JLabel("Password"));
		this.add(passwordField);
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(saveButton);

		saveButton.addActionListener(e -> {
			if (!isAllInfoCorrectlyEntered()) {
				JOptionPane.showMessageDialog(this, "Please enter all params.", "Cannot create key pair", JOptionPane.WARNING_MESSAGE);
			} else {
				saveKey();
			}
		});

		this.setSize(windowX, windowY);
		this.setResizable(false);
		this.setTitle(windowTitle);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
