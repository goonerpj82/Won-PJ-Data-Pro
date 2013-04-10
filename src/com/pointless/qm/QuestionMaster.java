package com.pointless.qm;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


import com.pointless.chat.ChatFilter;
import com.pointless.chat.ChatFilterType;
import com.pointless.chat.ChatIsLimitedException;
import com.pointless.exception.NotFoundException;
import com.pointless.io.Client;
import com.pointless.io.QuestionLoader;
import com.pointless.message.*;
import com.pointless.player.Player;
import com.pointless.quiz.Answer;
import com.pointless.quiz.Quiz;

/**
 * This class is to process game and control players
 * GUI is not necessary but it might be added in ver.2
 * @author Won Lee
 * @version 0.2 b032608w
 * b032413w:	Basic of Joining Game Protocol done.
 * b032608w:	Handling chat better, unicast chat is available.
 *
 */
public class QuestionMaster implements Runnable{
//public class QuestionMaster extends Thread{
	private Map<String,Client> clientMap = new HashMap<>();
	private List<JoinedPlayer> players = new ArrayList<>();
	private List<Team> teams = new ArrayList<>();
	private List<Quiz> quizList;
	private Quiz currentQuiz;
	private int currentRound = 0;
	private int currentPlayer = 0;
	private ChatFilter chatFilter = new ChatFilter();
	private boolean interrupt = false;
	private InetAddress localAddr = null;
	
	/**
	 * 
	 */
	public QuestionMaster(){
		quizList = QuestionLoader.load(new File("Quizes"));
	}
	
	/**
	 * 
	 * @param host IP address or name that accept player's connection
	 * @throws UnknownHostException
	 */
	public QuestionMaster(String host) throws UnknownHostException{
		this();
		localAddr = InetAddress.getByName(host);
	}
	
	public static void main(String[] arg) throws UnknownHostException{
		new Thread(new QuestionMaster()).start();
	}
	
	public void run() {
		try {
			int servPort = 53346;
			ServerSocket servSock;
			if(localAddr == null){
				servSock = new ServerSocket(servPort);
			}else{
				servSock = new ServerSocket(servPort, 50, localAddr);
			}
			
			while(true){
				System.out.println("Waiting for client ...");
				Socket playerSocket = servSock.accept();
				System.out.println("Client: " + playerSocket.toString() + " was connected");
				
				Client newConnection = new Client(playerSocket);
				String key = "" + playerSocket.getRemoteSocketAddress();
				if(clientMap.containsKey(key) || currentRound > 0){
					//close socket if connection to the host is already established.
					//Or game is already started.
					newConnection.closeSocket(EndType.DISCONNECT);
				}else{
					clientMap.put(key, newConnection);
					newConnection.addListener(new MessageEventListener(){
						public void messageEvent(MessageObject mo) throws IOException {
							messageFromClient(mo);
						}
					});
					Thread thread = new Thread(newConnection);
					thread.start();
					if(players.size() > 4){
						startGame();
					}
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
			sendPlayerStatus();
		}
	}
	
	/*
	 * Sending message about players status
	 */
	
	private void sendPlayerStatus() throws IOException{
		sendMessageToAll(new PlayerMessage("QM",generateCodesForPlayerMessage()));
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
			//Add player to list
			players.add(new JoinedPlayer(fm.getSrceName(),""+fm.getAddressKey()));
			//Set chat filter for the player to allow chatting
			chatFilter.changeFilter(fm.getSrceName(), ChatFilterType.Allow);
			//Send message that saying player requested name is confirmed 
			sendMessage(key, new FirstMessage("QuestionMaster",FirstType.CONFIRM));
			//Send all player information to everyone
			sendPlayerStatus();
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
			String destName = chme.getDestName();
			try {
				boolean ok = chatFilter.verifyChat(chme);
				if(ok){
					String key = getKeyByName(destName);
					sendMessage(key,chme);
				}else{
					String key = chme.getAddressKey().toString();
					ErrorMessage em = new ErrorMessage("QM", chatFilter.checkError(chme));
					sendMessage(key,em);
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (ChatIsLimitedException e) {
				e.printStackTrace();
			}
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
		/*
		 * Catch the error because player is already disconnected
		 */
		try {
			removePlayer(em.getAddressKey().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Player was disconnected and removed sucessfully");
	}
	
	/*
	 * Codes for QuizMessage
	 */
	
	private void nextQuiz(){
		Quiz quiz = null;
		//quiz = quizList.getByRound(currentRound);
		//currentQuiz = quiz;
		//sendQuizMessage(quiz);
	}
	
	/**
	 * 
	 * @param quiz
	 * @throws IOException
	 */
	private void sendQuizMessage(Quiz quiz) throws IOException{
		for(int i=0; i < players.size(); i++){
			if(i!=currentPlayer){
				sendMessage(players.get(i).getAddressKey(),new QuizMessage("QM", QuizMType.NOT_ACTIVE, quiz));
			}else{
				sendMessage(players.get(i).getAddressKey(),new QuizMessage("QM", QuizMType.ACTIVE, quiz));				
			}
		}
	}
	
	/*
	 * Codes for AnswerMessage
	 */
	/**
	 * 
	 * @param AnswerMessage am
	 * @throws IOException 
	 */
	private void getAnswerFromPlayer(AnswerMessage am) throws IOException{
		if(checkCurrentPlayer(am.getAddressKey().toString())){
			int score = currentQuiz.getAnswers().get(am.getIndex()).getPoint();
			players.get(currentPlayer).addScore(score);
			nextPlayer();
		}
	}
	
	/*
	 * Codes of functions to process the game
	 */
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void startGame() throws IOException{
		System.out.println("Game is started");
		currentRound = 1; //Round not Player
		Collections.shuffle(players);
		sendPlayerStatus();
		chat(new ChatMessage("QM", true, "", "Game is started with these players: " + players.toArray().toString()));
		//TODO get first question
		//quiz = quizList.getByRound(currentRound);
		//currentQuiz = quiz;
		//sendQuizMessage(quiz);
	}
	
	/**
	 * Increment currentPlayer, if it exceeds limit, set it back to 0
	 * and goes to next turn. At last, load next quiz
	 * @throws IOException
	 */
	public void nextPlayer() throws IOException{
		currentPlayer = currentPlayer % players.size();
		if(currentPlayer == 0){
			nextTurn();
		}
		sendPlayerStatus();
		nextQuiz();
	}
	
	/**
	 * 
	 * @param addressKey
	 * @throws IOException
	 */
	public void removePlayer(String addressKey) throws IOException{
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
			sendMessage(removed.getAddressKey(), new EndMessage("QM", EndType.LOSE));
			players.remove(removed);
		}
	}
	
	public void removePlayer(JoinedPlayer jp){
		players.remove(jp);
	}

	/**
	 * Every end of turn (means every player answered), list of players will be sorted by thier score
	 * And some player will be removed from the game.
	 * @throws IOException
	 */
	private void nextTurn() throws IOException{
		Collections.sort(players, new SortByScore());
		whoWillBeRemoved();
	}
	
	/**
	 * This is beta method. Algorithm to determine who should be removed is not completed yet.
	 * the goal is that at the last round, there is only 2 players left no matter 
	 * how many player exist at the start.
	 * @throws IOException 
	 */
	private void whoWillBeRemoved() throws IOException{
		//TODO think better algorithm to achieve that at the last round, there is only 2 players left no matter how many player exist at the start.
		if(currentRound == 1){
			
		}else if(currentRound == 2){
			
		}else if(currentRound == 3){
			removePlayer(players.get(3).getAddressKey());
		}else if(currentRound == 4){
			removePlayer(players.get(2).getAddressKey());
		}
	}

	/*
	 * miscellaneous
	 */
	
	/**
	 * Generate code that contains player name and score.
	 * @return
	 */
	private List<String> generateCodesForPlayerMessage(){
		List<String> codes = new ArrayList<>();
		for(JoinedPlayer jp: players){
			String code = jp.getName() + ":~><#" + jp.getScore();
			codes.add(code);
		}
		return codes;
	}
	
	/**
	 * Get key from player name
	 * @param name
	 * @return
	 * @throws NotFoundException
	 */
	public String getKeyByName(String name) throws NotFoundException{
		String st = null;
		for(JoinedPlayer jp: players){
			if(jp.getName().equals(name)){
				st = jp.getAddressKey();
			}
		}
		if(st != null){
			return st;			
		}else{
			throw new NotFoundException("Player <" + name + "> is not found.");
		}
	}
	
	/**
	 * Check sent message is from player who is possible to answer or not by key
	 * @param key 
	 * @return true if message source is possible to answer the question.
	 */
	private boolean checkCurrentPlayer(String key){
		if(players.get(currentPlayer).getAddressKey().equals(key)){
			return true;
		}
		return false;
	}
	
}
