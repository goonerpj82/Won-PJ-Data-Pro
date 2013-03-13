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
import com.pointless.quiz.Answer;

import javax.swing.JLayeredPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * All necessary information to generate GUi is given by QuestionMasterGui instance.
 * Encapsulate chat from ChatPane, playerAnswer from QuestionPane for QuestionMasterGui
 * which means encapsulate them for QuestionMaster class.
 * @author Won
 *
 */
public class PlayerGui extends JFrame {
	
	private Player player;
	private ChatPane chatPane;
	private OtherTeamInfoPane otip;
	private JPanel contentPane;
	private MainDisplayPane mainDisplayPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGui frame = new PlayerGui(null);
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
	public PlayerGui(Player constPlayer) {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Hey");
				player.closing();
			}
		});
		
		setResizable(false);
		player = constPlayer;
		player.addChatListener(new ChatListener(){
			public void chatEvent(Chat chat) {
				chatPane.receiveMessage(chat);
			}
			public void chatEvent(Player dest, String message, boolean toAll) {}
		});
		
		//player = new Player("Won Lee");
		Player playerb = new Player("PJ");
		Team team = new Team(player, playerb);
		
		List<Team> teams = new ArrayList<Team>();
		teams.add(team);
		teams.add(team);
		teams.add(team);
		teams.add(team);

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
		chatPane.addChatListener(new ChatListener(){
			public void chatEvent(Chat chat) {}
			public void chatEvent(Player dest, String message, boolean toAll) {
				System.out.println("PlayerGui heard ChatEvent");
				Chat chat = new Chat(player, dest, message, toAll);
				player.passChatToMaster(chat);
			}
		});
		layeredPane.add(chatPane);
		
		mainDisplayPane = new MainDisplayPane();
		mainDisplayPane.setBounds(0, 0, 494, 220);
		mainDisplayPane.addGuiAnswerListener(new GuiAnswerListener(){
			public void guiAnswered(Answer guiAnswer) {
				
			}
		});
		layeredPane.add(mainDisplayPane);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void relayMessage(Chat chat){
		chatPane.receiveMessage(chat);
	}
	
	public void showNotification(String message){
		
	}
}
