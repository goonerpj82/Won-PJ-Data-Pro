package com.pointless.player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JTextField;

import com.pointless.message.MessageObject;
import com.pointless.message.MessageEventListener;

public class ClientGui extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;
	private Client client;
	private JTextField textField;
	private JButton btnStart;
	private boolean accept = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGui frame = new ClientGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public ClientGui() throws ClassNotFoundException{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//GroovyClientTest gct = new GroovyClientTest();
				//gct.send3message(client);
				//*
				try {
					client.sendMessage(new MessageObject("Client"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				//*/
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.NORTH);
		textField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JTextField tf = (JTextField) arg0.getSource();
				connectToMaster(tf.getText());
			}
		});
		textField.setColumns(10);
		
		btnStart = new JButton("End");
		btnStart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					client.closeSocket();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		contentPane.add(btnStart, BorderLayout.EAST);
	}
	
	private void connectToMaster(String st){
		try {
			client = new Client(st);
			client.addListener(new MessageEventListener(){
				public void messageEvent(MessageObject mo) {
					updateTextPane(mo);
				}
			});
			new Thread(client).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateTextPane(MessageObject mo){
		textPane.setText(textPane.getText()+"\n"+mo.toString());
	}
}
