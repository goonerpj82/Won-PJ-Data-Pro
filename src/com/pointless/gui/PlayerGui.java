package com.pointless.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatListener;

public class PlayerGui extends JFrame {
	
	private PlayerAnswerListener paListener;
	private ChatListener chatListener;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGui frame = new PlayerGui();
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
	public PlayerGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	private void playerAnswered(){
		if(paListener != null){
			//paListener;
		}
	}
	public void addPlayerAnswerListener(PlayerAnswerListener paListener){
		this.paListener = paListener;
	}

	private void sendChat(Chat chat){
		if(chatListener != null){
			chatListener.sendChat(chat);
		}
	}
	public void addChatListener(ChatListener chatListener){
		this.chatListener = chatListener;
	}
}
