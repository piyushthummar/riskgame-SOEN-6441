package com.riskgame.view;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class EditMapMenu extends JFrame {
	private JPanel contentPane;
	
	/**
	 * Create the panel.
	 */
	public EditMapMenu() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel(new GridLayout(3, 3, 3, 3));
		contentPane.add(panel, BorderLayout.CENTER);

		JButton btnNewButton_1 = new JButton("Create new map");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditMap edit = new EditMap();
				edit.setVisible(true);
			}
		});
		panel.add(btnNewButton_1);

		JButton btnNewButton_3 = new JButton("Edit Existing Map");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		panel.add(btnNewButton_3);

		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menus menu = new Menus();
				menu.setVisible(true);
			}
		});
		panel.add(btnNewButton_2);

	}
	

}
