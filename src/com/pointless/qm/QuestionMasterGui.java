package com.pointless.qm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pointless.chat.ChatFilter;
import com.pointless.chat.ChatFilterType;
import com.pointless.chat.ChatIsLimitedException;
import com.pointless.player.Player;
import com.pointless.player.PlayerGui;
import com.pointless.quiz.Answer;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.swing.JTextPane;

/**
 * This class hold QuestionMaster instance and get necessary information for PlayerGui from it.
 * All method to proceed the game will be implemented in QuestionMaster Class and this class.
 * 
 * 
 * @author Won Lee
 *
 */
public class QuestionMasterGui extends JFrame {

	private JPanel contentPane;
	private Map<Player,PlayerGui> mapOfPP = new HashMap<Player,PlayerGui>();
	private QuestionMaster qm = new QuestionMaster();
	private JTextField txtAddPlayer;
	private JTextField txf_message;
	private JLayeredPane chatPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuestionMasterGui frame = new QuestionMasterGui();
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
	public QuestionMasterGui() {		
		
		System.out.println(qm.getQuizList().size());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtAddPlayer = new JTextField();
		txtAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField txt = (JTextField) arg0.getSource();
				//if(qm.matchPlayerByName(txt.getText()) == null){
					//addPlayer(new Player(txt.getText()));
				//}
				try {
					qm.sendMessage(txt.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				txt.setText("");
			}
		});
		txtAddPlayer.setText("Add Player");
		txtAddPlayer.setBounds(10, 11, 86, 20);
		contentPane.add(txtAddPlayer);
		txtAddPlayer.setColumns(10);
		
		/*
		 * Chat Control Panel
		 */
		chatPane = new JLayeredPane();
		chatPane.setBounds(251, 11, 173, 240);
		contentPane.add(chatPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(0, 0, 173, 217);
		chatPane.add(textPane);
		
		txf_message = new JTextField();
		txf_message.addActionListener(new ActionListener() {
			/**
			 * Sends Message to All Player, Master to one Player chat will be implemented later
			 */
			public void actionPerformed(ActionEvent arg0) {
				JTextField txf = (JTextField) arg0.getSource();
				//for(Player player: qm.getPlayers()){
					//player.receiveChat(new Chat(new Player("Master"), player, txf.getText(), false));
				//}
				txf.setText("");
			}
		});
		txf_message.setBounds(0, 220, 173, 20);
		chatPane.add(txf_message);
		txf_message.setColumns(10);
		
		new Thread(qm).start();
	}
	
	
}
