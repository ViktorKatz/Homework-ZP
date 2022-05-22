package etf.openpgp.kv180141dmd180153d;

import java.awt.Label;

import javax.swing.*;

public class GUI {

	private String window_title = "ZP domaci";
	
	private final static int windowX = 800;
	private final static int windowY = 800;
	private final static int horizontalBreak = 600;
	
	private JPanel privateKeyRingTab = new PrivateKeyRingPanel();
	
	
	public GUI() {
	    JFrame window_frame = new JFrame();
	    JPanel public_key_ring_tab = new JPanel();
	    
	    JTabbedPane tabs = new JTabbedPane();
	    tabs.setBounds(0, 0, windowX, horizontalBreak);
	    tabs.add("Private Key Ring", privateKeyRingTab);
	    tabs.add("Public Key Ring", public_key_ring_tab);
	    
	    JPanel downTest = new JPanel();
	    downTest.add(new Label("asdasdasdasd"));
	    downTest.setBounds(0, horizontalBreak, windowX, windowY-horizontalBreak);
	    
	    window_frame.add(tabs);
	    window_frame.add(downTest);
	    
	    window_frame.setSize(windowX, windowY);
	    window_frame.setTitle(window_title);
	    window_frame.setLayout(null);
	    window_frame.setVisible(true);
	}
	public static void main(String[] args) {
		new GUI();

	}

}
