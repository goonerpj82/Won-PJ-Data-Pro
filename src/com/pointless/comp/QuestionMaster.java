package com.pointless.comp;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatFilter;
import com.pointless.chat.ChatFilterType;
import com.pointless.chat.ChatIsLimitedException;
import com.pointless.chat.ChatListener;
import com.pointless.communication.Client;
import com.pointless.communication.MessageObject;
import com.pointless.communication.ServerEventListener;
import com.pointless.io.QuestionLoader;
import com.pointless.quiz.Answer;
import com.pointless.quiz.Quiz;

public class QuestionMaster{
	private Map<String,Client> players = new HashMap<>();
	private List<Team> teams = new ArrayList<Team>();
	private List<Quiz> quizList;
	private int currentRound;
	private ChatFilter chatFilter = new ChatFilter();
	private boolean interrupt = false;
	private ChatListener chatListener;
	
	public QuestionMaster(){
		quizList = QuestionLoader.load(new File("Quizes"));
	}
	
	public void run() {
		try {
			int servPort = 53346;
			ServerSocket servSock = new ServerSocket(servPort);
			
			while(true){
				System.out.println("Waiting for client ...");
				Socket playerSocket = servSock.accept();
				System.out.println("Client: " + playerSocket.toString() + " was connected");
				
				Client newConnection = new Client(playerSocket);
				newConnection.addListener(new ServerEventListener(){
					public void serverEvent(MessageObject mo) {
						messageFromClient(mo);
					}
				});
				Thread thread = new Thread(newConnection);
				clients.add(newConnection);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @return the quizList
	 */
	public List<Quiz> getQuizList() {
		return quizList;
	}

	/**
	 * @param quizList the quizList to set
	 */
	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}

	/**
	 * @return the teams
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	/**
	 * @return the currentRound
	 */
	public int getCurrentRound() {
		return currentRound;
	}

	/**
	 * @param currentRound the currentRound to set
	 */
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	/**
	 * @return the interrupt
	 */
	public boolean isInterrupt() {
		return interrupt;
	}

	/**
	 * @param interrupt the interrupt to set
	 */
	public void setInterrupt(boolean interrupt) {
		this.interrupt = interrupt;
	}

	/**
	 * @return the chatFilter
	 */
	public ChatFilter getChatFilter() {
		return chatFilter;
	}

	/**
	 * @param chatFilter the chatFilter to set
	 */
	public void setChatFilter(ChatFilter chatFilter) {
		this.chatFilter = chatFilter;
	}

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public void addPlayer(Player player){
		System.out.println("Ob: "+player.countObservers());
		player.addObserver(new Observer(){
			public void update(Observable arg0, Object arg1) {
				filter(arg0, arg1);
			}
		});
		chatFilter.changeSourceFilter(player, ChatFilterType.Allow);
		chatFilter.changeDestinationFilter(player, ChatFilterType.Allow);
		players.add(player);
	}
	
	public void createTeam(Player player1){
		teams.add(new Team(player1));
	}
	public void createTeam(Player player1, Player player2){
		teams.add(new Team(player1, player2));
	}
	
	public void startGame(){
		for(Player player: players){
			teams.add(new Team(player));
		}
		for(Player player: players){
			
		}
	}
	
	public Player matchPlayerByName(String name){
		for(Player player: players){
			if(player.getName().equals(name)){
				return player;
			}
		}
		return null;
	}
	
	public void filter(Observable arg0, Object arg1) {
		Player p1 = (Player) arg0;
		System.out.println("QM heard "+arg1.toString()+" from "+p1.getName());
		if(arg1 instanceof Chat){
			Chat chat = (Chat) arg1;
			try {
				if(interrupt){
					verifyChatByGui(chat);
				}if(chatFilter.verifyChat(chat)){
					System.out.println("Chat Verified");
					//chat.getSource().receiveChat(chat);
					sendChat(chat.getSource(),chat);
				}else{
					
				}
			} catch (ChatIsLimitedException e) {
				e.printStackTrace();
			}
		}
		if(arg1 instanceof Answer){
			/*
			if(p1.equals(currentPlayer){
				
			}
			*/
		}
		if(arg1.equals("Bye Bye")){
			System.out.println(arg1.toString());
			players.remove(p1);
		}
	}
	
	public void addChatListener(ChatListener chatListener){
		this.chatListener = chatListener;
	}
	private void verifyChatByGui(Chat chat){
		if(chatListener != null){
			chatListener.chatEvent(chat);
		}
	}
	public void sendChat(Player player, Chat chat){
		System.out.println("Sending Chat from QuestionMaster");
		player.receiveChat(chat);
	}

}
