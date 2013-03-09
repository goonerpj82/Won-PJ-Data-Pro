package com.pointless.gui.test;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class NewPane extends JPanel {

	private JLabel lblNewLabel;
	/**
	 * Create the panel.
	 */
	public NewPane(String st) {
		
		lblNewLabel = new JLabel(st);
		add(lblNewLabel);

	}

}
