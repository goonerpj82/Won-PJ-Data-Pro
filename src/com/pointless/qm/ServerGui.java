package com.pointless.qm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import com.pointless.message.MessageObject;
import com.pointless.message.MessageEventListener;

public class ServerGui extends JFrame {

	private JPanel contentPane;
	private JButton btnStatus;
	private JTextPane textPane;
	private Server server;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui frame = new ServerGui();
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
	 */
	public ServerGui() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		contentPane.add(textPane, BorderLayout.CENTER);
		
		btnStatus = new JButton("Not Waiting");
		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					server.sendMessage(Integer.valueOf(textField.getText()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnStatus, BorderLayout.SOUTH);
		
		textField = new JTextField();
		textField.setText("0");
		contentPane.add(textField, BorderLayout.WEST);
		textField.setColumns(10);
		
		launchServer();
	}

	public void launchServer() throws IOException {
		server = new Server();
		server.addListener(new MessageEventListener(){
			public void messageEvent(MessageObject mo) {
				System.out.println("Heared Event at ServerGui");
				textPane.setText(textPane.getText()+"\n"+mo.toString());
			}
		});
		new Thread(server).start();
	}

}
