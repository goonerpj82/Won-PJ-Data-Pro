package com.pointless.gui.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ExSubFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextPane textPane;
	private ChatListener chatLi;
	private JPanel panel;
	private JLabel label;

	/**
	 * Create the frame.
	 */
	public ExSubFrame() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Chat chat = new Chat("From","To",textField.getText());
				sendText(chat);
				textField.setText("");
			}
		});
		contentPane.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		
		label = new JLabel("123");
		panel.add(label);
	}
	
	public void newText(String st){
		textField.setText(st);
	}
	
	public void addChatListener(ChatListener chatLi){
		this.chatLi = chatLi;
	}
	
	public void receiveChat(Chat chat){
		String st = "From: "+chat.getSource()+"  Destination: "+chat.getDestination()+"\n" +
				""+chat.getMessage()+"\n";
		textPane.setText(textPane.getText()+st);
	}

	private void sendText(Chat chat){
		if(chatLi != null){
			chatLi.sendText(chat);
		}
	}
	
	public void eastPane(JPanel pane){
		panel.removeAll();
		panel.add(pane);
		this.revalidate();
	}
}
