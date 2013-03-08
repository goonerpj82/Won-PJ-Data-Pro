package com.pointless.gui.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JTextPane;

public class SubFrameSizeChangeTest extends JFrame {

	private JPanel contentPane;
	private ExSubFrame subFrame1;
	private ExSubFrame subFrame2;
	private JTextPane textPane;

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
		
		textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		
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
