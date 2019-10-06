package com.riskgame.view;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class EditMap extends JFrame {
	

	/**
	 * Create the panel.
	 */
	public EditMap() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JRadioButton rdbtnEditNewFile = new JRadioButton("Edit new File");
		
		JRadioButton rdbtnBrowseExistingFile = new JRadioButton("Browse Existing file");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(60)
					.addComponent(rdbtnEditNewFile)
					.addGap(35)
					.addComponent(rdbtnBrowseExistingFile))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(rdbtnEditNewFile)
				.addComponent(rdbtnBrowseExistingFile)
		);
		getContentPane().setLayout(groupLayout);
		
		
	}

}
