package com.pointless.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatFilter;
import com.pointless.chat.ChatFilterType;
import com.pointless.chat.ChatIsLimitedException;
import com.pointless.chat.ChatListener;
import com.pointless.comp.Player;
import com.pointless.comp.QuestionMaster;
import com.pointless.comp.Team;

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
	private ChatFilter chatFilter;
	private List<PlayerGui> pGuis;
	private Map<Player,PlayerGui> mapOfPP;
	private QuestionMaster qm;

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
		chatFilter = new ChatFilter();
		pGuis = new ArrayList<PlayerGui>();
		
		qm = new QuestionMaster();
		System.out.println(qm.getQuizList().size());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	private void startGame(List<Player> players, List<Team> teams){
		
		
		for(Player player: players){
			PlayerGui pGui = new PlayerGui(player, teams);
			pGui.addChatListener(new ChatListener(){
				public void chatEvent(Chat chat) {
					verifyChat(chat);
				}
				public void chatEvent(Player dest, String message, boolean toAll) {}
			});
			pGui.addPlayerAnswerListener(new PlayerAnswerListener(){
				public void playerAnswered() {
					
				}
			});
			mapOfPP.put(player, pGui);
			chatFilter.changeDestinationFilter(player, ChatFilterType.Allow);
			chatFilter.changeSourceFilter(player, ChatFilterType.Allow);
		}
	}
	
	/**
	 * Method to check the source is allowed to send chat and the destination is allowed to accept chat
	 * @param chat
	 */
	private void verifyChat(Chat chat){
		if(chat.isToAll()){
			for(PlayerGui pGui: pGuis){
				chat.setDestination(pGui.getPlayer());
				try {
					if(chatFilter.verifyChat(chat)){
						pGui.relayMessage(chat);
					}
				} catch (ChatIsLimitedException e) {
					e.printStackTrace();
				}
			}
		}else{
		boolean ok = false;
			try {
				ok = chatFilter.verifyChat(chat);
			} catch (ChatIsLimitedException e) {
				ok = false;
			} finally {
				if(ok){
					mapOfPP.get(chat.getDestination()).relayMessage(chat);
				}else{
					mapOfPP.get(chat.getSource()).showNotification(chat.getDestination().getName()+
							" cannot accept message.");
				}
			}
		}
	}

}
