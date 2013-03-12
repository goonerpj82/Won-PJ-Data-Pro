package com.pointless.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatListener;
import com.pointless.comp.Player;
import com.pointless.comp.Team;

import javax.swing.JLayeredPane;

/**
 * All necessary information to generate GUi is given by QuestionMasterGui instance.
 * Encapsulate chat from ChatPane, playerAnswer from QuestionPane for QuestionMasterGui
 * which means encapsulate them for QuestionMaster class.
 * @author Won
 *
 */
public class PlayerGui extends JFrame {
	
	private Player player;
	private PlayerAnswerListener paListener;
	private ChatListener chatListener;
	private ChatPane chatPane;
	private OtherTeamInfoPane otip;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGui frame = new PlayerGui(null,null);
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
	public PlayerGui(Player constPlayer, List<Team> teams) {
		setResizable(false);
		player = constPlayer;
		player = new Player(0, "Won Lee");
		Player playerb = new Player(0, "PJ");
		Team team = new Team(player, playerb);
		teams = new ArrayList<Team>();
		teams.add(team);
		teams.add(team);
		teams.add(team);
		teams.add(team);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 494, 372);
		contentPane.add(layeredPane);
		
		otip = new OtherTeamInfoPane(teams);
		otip.setBounds(0, 222, 145, 150);
		otip.addDestListener(new DestinationClickedListener(){
			public void destClicked(Player dest) {
				chatPane.changeDest(dest);
				//relayMessage(new Chat(player, player, "Clicked", false));
			}
		});
		layeredPane.add(otip);
		
		chatPane = new ChatPane(teams);
		chatPane.setBounds(144, 222, 350, 150);
		layeredPane.add(chatPane);
		
		JPanel mainDisplayPane = new JPanel();
		mainDisplayPane.setBounds(0, 0, 494, 222);
		layeredPane.add(mainDisplayPane);
		chatPane.addChatListener(new ChatListener(){
			public void chatEvent(Chat chat) {}
			public void chatEvent(Player dest, String message, boolean toAll) {
				System.out.println("PlayerGui heard ChatEvent");
				Chat chat = new Chat(player, dest, message, toAll);
				passChatToMaster(chat);
			}
		});
	}
	
	public Player getPlayer(){
		return player;
	}
	
	private void playerAnswered(){
		if(paListener != null){
			//paListener;
		}
	}
	public void addPlayerAnswerListener(PlayerAnswerListener paListener){
		this.paListener = paListener;
	}

	private void passChatToMaster(Chat chat){
		if(chatListener != null){
			chatListener.chatEvent(chat);
		}
	}
	public void addChatListener(ChatListener chatListener){
		this.chatListener = chatListener;
	}
	
	public void relayMessage(Chat chat){
		chatPane.receiveMessage(chat);
	}
	
	public void showNotification(String message){
		
	}
}
