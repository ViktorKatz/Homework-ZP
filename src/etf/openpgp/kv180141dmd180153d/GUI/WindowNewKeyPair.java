package etf.openpgp.kv180141dmd180153d.GUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import etf.openpgp.kv180141dmd180153d.Constants;
import etf.openpgp.kv180141dmd180153d.algorithms.IAsymmetricKeyAlgorithm;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WindowNewKeyPair extends JFrame {

	private static final long serialVersionUID = 7954094844062145591L;

	private final static String windowTitle = "Create new key pair";
	private final static int windowX = 350;
	private final static int windowY = 200;

	private static JTextField keyNameField = new JTextField();
	private static JTextField emailField = new JTextField();
	private static JComboBox<IAsymmetricKeyAlgorithm> algoritmsBox = new JComboBox<IAsymmetricKeyAlgorithm>(Constants.supportedAsymetricAlgorithms);
	private static JPasswordField passwordField = new JPasswordField();
	private static JButton saveButton = new JButton("Save");

	private boolean isAllInfoCorrectlyEntered() {
		if (keyNameField.getText().isEmpty())
			return false;
		if (!emailField.getText().matches(".+[@].+[.].+"))
			return false;
		if (String.valueOf(passwordField.getPassword()).isEmpty())
			return false;
		return true;
	}
	
	private void saveKey() {
		throw new NotImplementedException();
	}

	public WindowNewKeyPair() {
		this.setLayout(new GridLayout(6, 2));

		this.add(new JLabel("Key name"));
		this.add(keyNameField);
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
			}
			else {
				saveKey();
			}
		});

		this.setSize(windowX, windowY);
		this.setTitle(windowTitle);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
