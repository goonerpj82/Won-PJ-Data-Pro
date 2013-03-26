package com.pointless.qm;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.pointless.chat.ChatFilter;
import com.pointless.chat.ChatFilterType;
import com.pointless.chat.ChatIsLimitedException;
import com.pointless.io.Client;
import com.pointless.io.QuestionLoader;
import com.pointless.message.*;
import com.pointless.player.Player;
import com.pointless.quiz.Answer;
import com.pointless.quiz.Quiz;

/**
 * 
 * @author Won Lee
 * @version 0.2 b032413w
 * b032413w:	Basic of Joining Game Protocol done.
 *
 */
public class QuestionMaster implements Runnable{
//public class QuestionMaster extends Thread{
	private Map<String,Client> clientMap = new HashMap<>();
	private List<JoinedPlayer> players = new ArrayList<>();
	private List<Team> teams = new ArrayList<>();
	private List<Quiz> quizList;
	private int currentRound;
	private ChatFilter chatFilter = new ChatFilter();
	private boolean interrupt = false;
	
	public QuestionMaster(){
		quizList = QuestionLoader.load(new File("Quizes"));
	}
	
	public static void main(String[] arg){
		new Thread(new QuestionMaster()).start();
		//new QuestionMaster().start();
	}
	
	public void run() {
		try {
			int servPort = 53346;
			ServerSocket servSock = new ServerSocket(servPort);
			
			while(true){
				System.out.println("Number of Players " + players.size() + " and Connections " + clientMap.size());
				System.out.println("Waiting for client ...");
				Socket playerSocket = servSock.accept();
				System.out.println("Client: " + playerSocket.toString() + " was connected");
				
				Client newConnection = new Client(playerSocket);
				String key = "" + playerSocket.getRemoteSocketAddress();
				if(clientMap.containsKey(key)){
					//close socket if connection to the host is already established.
					newConnection.closeSocket(EndType.DISCONNECT);
				}else{
					System.out.println("The Key is " + key);
					clientMap.put(key, newConnection);
					newConnection.addListener(new MessageEventListener(){
						public void messageEvent(MessageObject mo) throws IOException {
							messageFromClient(mo);
						}
					});
					Thread thread = new Thread(newConnection);
					thread.start();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Getters and Setters
	 */

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
	
	/*
	 * Codes for sending Message and dealing with received Message
	 */
	
	public void sendMessageToAll(MessageObject mo) throws IOException{
		Collection clients = clientMap.values();
		for(Iterator i = clients.iterator(); i.hasNext();){
			Client cl = (Client) i.next();
			cl.sendMessage(mo);
		}
	}
	public void sendMessage(String addressKey, MessageObject mo) throws IOException{
		clientMap.get(addressKey).sendMessage(mo);
	}
	public void sendMessage(String key) throws IOException{
		clientMap.get(key).sendMessage(new MessageObject("QM"));
	}
	public void sendMessage(JoinedPlayer jp) throws IOException{
		sendMessage(jp.getAddressKey());
	}
	
	
	/**
	 * Filter the message depending on the Message Class
	 * @param mo
	 * @throws IOException
	 */
	public void messageFromClient(MessageObject mo) throws IOException{
		if(mo instanceof FirstMessage){
			System.out.println("Join Request");
			addPlayer((FirstMessage) mo);
		}else if(mo instanceof ChatMessage){
			ChatMessage chme = (ChatMessage) mo;
			chat(chme);
		}else if(mo instanceof EndMessage){
			System.out.println("Closing Notification");
			playerDisconnected((EndMessage) mo);
			sendMessageToAll(new PlayerMessage("QM",generateCodesForPlayerMessage()));
		}
	}
	
	/*
	 * Dealing with FirstMessage
	 */
	
	/**
	 * Check player name is available and if it is, add player and send CONFIRM message.
	 * If not, send REJECT message.
	 * @param fm
	 * @throws IOException
	 */
	public void addPlayer(FirstMessage fm) throws IOException{
		boolean foundName = false;
		for(JoinedPlayer jp: players){
			if(jp.getName().equals(fm.getSrceName())){
				foundName = true;
				break;
			}
		}
		String key = fm.getAddressKey().toString();
		if(!foundName){
			players.add(new JoinedPlayer(fm.getSrceName(),""+fm.getAddressKey()));
			sendMessage(key, new FirstMessage("QuestionMaster",FirstType.CONFIRM));
			sendMessageToAll(new PlayerMessage("QM",generateCodesForPlayerMessage()));
		}else{
			sendMessage(key, new FirstMessage("QuestionMaster",FirstType.REJECT));
		}
		System.out.println("Number of Players " + players.size() + " and Connections " + clientMap.size());
	}
	
	/*
	 * Deal with ChatMessage
	 */
	
	/**
	 * Check ChatMessage and decide it is ok to send or not.
	 * Filtering is not yet implemented. 
	 * @param chme
	 * @throws IOException
	 */
	public void chat(ChatMessage chme) throws IOException{
		if(!chme.isToAll()){
			//if chat is not for all player
			String key = "";
			for(JoinedPlayer jp: players){
				if(jp.getName().equals(chme.getDestName())){
					key = jp.getAddressKey();
				}
			}
			sendMessage(key,chme);
		}else{
			//if chat is for all player
			sendMessageToAll(chme);
		}
	}
	
	/*
	 * Deal with EndMessage
	 */
	
	public void playerDisconnected(EndMessage em){
		System.out.println("disconnecting player");
		System.out.println("Key of EndMessage: " + em.getAddressKey());
		clientMap.remove(""+em.getAddressKey());
		removePlayer(em.getAddressKey().toString());
		System.out.println("Player was disconnected and removed sucessfully");
	}
	
	/*
	 * Codes of functions to process the game
	 */
	
	private List<String> generateCodesForPlayerMessage(){
		List<String> codes = new ArrayList<>();
		for(JoinedPlayer jp: players){
			String code = jp.getName() + ":~><#" + jp.getScore();
			codes.add(code);
		}
		return codes;
	}
	
	public void removePlayer(String addressKey){
		JoinedPlayer removed = null;
		for(JoinedPlayer jp: players){
			if(jp.getAddressKey().equals(addressKey)){
				/*
				 * We cannot delete element from list/map during the for loop
				 * because it cause concurrentModificationException.
				 * This didn't happen Java1.6 but it was a known bug?
				 */
				removed = jp;
			}
		}
		if(removed != null){
			players.remove(removed);
		}
	}
	public void removePlayer(JoinedPlayer jp){
		players.remove(jp);
	}


}
