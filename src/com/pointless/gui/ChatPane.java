package com.pointless.gui;

import javax.swing.JPanel;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatListener;
import com.pointless.comp.Player;
import com.pointless.comp.Team;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ScrollPaneConstants;

/**
 * JPanel specialized to send and show messages.
 * @author Won
 *
 */
public class ChatPane extends JPanel {
	private ChatListener chatListener;
	private JTextField messageEntry;
	private JTextPane messageDisplay;

	/**
	 * Create the panel.
	 */
	public ChatPane(List<Team> teams) {
		setLayout(null);
		
		List<Player> players = new ArrayList<Player>();
		for(Team team: teams){
			players.addAll(team.getPlayers());
		}
		Collections.sort(players);
		JComboBox destBox = new JComboBox(players.toArray());
		destBox.setBounds(5, 125, 70, 20);
		add(destBox);
		
		messageEntry = new JTextField();
		messageEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField jtf = (JTextField) arg0.getSource();
				sendChat(jtf.getText());
				receiveMessage(new Chat(null,null,jtf.getText(),false));
				jtf.setText("");
			}
		});
		messageEntry.setBounds(75, 125, 270, 20);
		add(messageEntry);
		messageEntry.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(5, 5, 340, 120);
		add(scrollPane);
		
		messageDisplay = new JTextPane();
		messageDisplay.setEditable(false);
		messageDisplay.setText("Welcome to Pointless made by Won & PJ\n");
		scrollPane.setViewportView(messageDisplay);

	}
	
	private void sendChat(String message){
		if(chatListener != null){
			chatListener.chatEvent(null, message, false);
		}
	}
	public void addChatListener(ChatListener chatListener){
		this.chatListener = chatListener;
	}
	
	public void receiveMessage(Chat chat){
		String show = "From: " + chat.getSource().getName() + "... " + chat.getMessage()+"\n";
		String existText = messageDisplay.getText();
		int length = existText.length();
		if(length > 1000){
			existText = existText.substring(show.length(), length);
		}
		messageDisplay.setText(existText+show);
	}
	
	public void changeDest(Player player){
		
	}
}