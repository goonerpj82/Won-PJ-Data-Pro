package com.pointless.gui.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JTextPane;
import javax.swing.JLayeredPane;
import javax.swing.JProgressBar;

public class SubFrameSizeChangeTest extends JFrame {

	private JPanel contentPane;
	private ExSubFrame subFrame1;
	private ExSubFrame subFrame2;
	private JTextPane textPane;
	private int count = 1;
	private JProgressBar progressBar;
	private int percent = 0;
	private JLabel lblHere;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubFrameSizeChangeTest frame = new SubFrameSizeChangeTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SubFrameSizeChangeTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnBigger = new JButton("Bigger");
		btnBigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				subFrame1.setSize(subFrame1.getWidth()+50, subFrame1.getHeight()+50);
				subFrame1.newText(""+(subFrame1.getWidth()+50));
			}
		});
		contentPane.add(btnBigger, BorderLayout.WEST);
		
		JButton btnSmaller = new JButton("Smaller");
		btnSmaller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subFrame1.setSize(subFrame1.getWidth()-50, subFrame1.getHeight()-50);
				subFrame1.newText(""+(subFrame1.getWidth()-50));
			}
		});
		contentPane.add(btnSmaller, BorderLayout.EAST);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewPane pane1 = new NewPane(""+(count++));
				subFrame2.eastPane(pane1);
				percent += 20;
				if(percent > 0 && percent <= 100){
					progressBar.setVisible(true);
					progressBar.setValue(percent);
				}else{
					progressBar.setVisible(false);
					percent = 0;
				}
				if(lblHere.getText().length()>10){
					lblHere.setText("");
				}else{
					lblHere.setText(lblHere.getText()+".");
				}
					
			}
		});
		contentPane.add(btnAdd, BorderLayout.SOUTH);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		textPane.setBounds(0, 0, 294, 218);
		layeredPane.add(textPane);
		
		progressBar = new JProgressBar();
		layeredPane.setLayer(progressBar, 1);
		progressBar.setBounds(40, 72, 146, 14);
		layeredPane.add(progressBar);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("New button Clicked");
			}
		});
		layeredPane.setLayer(btnNewButton, 1);
		btnNewButton.setBounds(125, 127, 89, 23);
		layeredPane.add(btnNewButton);
		
		lblHere = new JLabel("here");
		layeredPane.setLayer(lblHere, 1);
		lblHere.setBounds(40, 165, 46, 14);
		layeredPane.add(lblHere);
		
		subFrame1 = new ExSubFrame();
		subFrame1.addChatListener(new ChatListener(){
			public void sendText(Chat chat) {
				textPane.setText(textPane.getText()+chat.toString()+"\n");
				subFrame2.receiveChat(chat);
				subFrame1.receiveChat(chat);
			}
		});
		//subFrame1.setEnabled(false);
		subFrame1.setSize(300, 300);
		subFrame1.setTitle("SF 1");
		subFrame1.setVisible(true);
		
		subFrame2 = new ExSubFrame();
		subFrame2.addChatListener(new ChatListener(){
			public void sendText(Chat chat) {
				textPane.setText(textPane.getText()+chat.toString()+"\n");
				subFrame2.receiveChat(chat);
				subFrame1.receiveChat(chat);
			}
		});
		subFrame2.setSize(300, 300);
		subFrame2.setTitle("SF 2");
		subFrame2.setVisible(true);		
		HashMap<String,String> stst = new HashMap<String,String>();
	}
}
