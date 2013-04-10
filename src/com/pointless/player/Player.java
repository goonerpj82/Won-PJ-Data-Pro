package com.pointless.player;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pointless.io.Client;
import com.pointless.message.*;
import com.pointless.quiz.Quiz;

/**
 * 
 * @author Won
 * @version 0.2 b032413w
 * b032413w:	Basic of Joining Game Protocol Done
 * b040816w:	Chat logging bug was fixed
 *
 */
public class Player implements Serializable{
	private int id;
	private String name;
	private boolean myTurn;
	private Quiz givenQuiz;
	private List<String> otherPlayers;
	private Client client;
	private String chatLog;
	private int chatMax;
	private MessageEventListener listener;

	public Player(String name) {
		this();
		this.name = name;
	}
	public Player(){
		chatLog = "Welcome to Pointless Game by Won & PJ\n";
		chatMax = 2000;
		otherPlayers = new ArrayList<>();
	}
	
	/*
	 * Getters and Setters
	 */
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the myTurn
	 */
	public boolean isMyTurn() {
		return myTurn;
	}

	/**
	 * @param myTurn the myTurn to set
	 */
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	/**
	 * @return the givenQuiz
	 */
	public Quiz getGivenQuiz() {
		return givenQuiz;
	}

	/**
	 * @param givenQuiz the givenQuiz to set
	 */
	public void setGivenQuiz(Quiz givenQuiz) {
		this.givenQuiz = givenQuiz;
	}
	
	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the otherPlayers
	 */
	public List<String> getOtherPlayers() {
		return otherPlayers;
	}

	/**
	 * @return the chatMax
	 */
	public int getChatMax() {
		return chatMax;
	}
	/**
	 * @param chatMax the chatMax to set
	 */
	public void setChatMax(int chatMax) {
		this.chatMax = chatMax;
	}
	/**
	 * @return the chatLog
	 */
	public String getChatLog() {
		return chatLog;
	}
	/**
	 * @param chatLog the chatLog to set
	 */
	public void setChatLog(String chatLog) {
		this.chatLog = chatLog;
	}
	
	
	/*
	 * Methods to join
	 */

	/**
	 * 
	 * @param addr QuestionMaster's IP Address
	 * @param name Player's Name
	 * @return true if name is available, false if not.
	 * @throws IOException when there is problem with sending or receiving.
	 * @throws ClassNotFoundException 
	 */
	public boolean joinGame(String addr, String name) throws IOException, ClassNotFoundException{
		client = new Client(addr);
		client.addListener(new MessageEventListener(){
			public void messageEvent(MessageObject mo) throws IOException {
				handleMessage(mo);
			}
		});
		boolean ok = client.nameNegotiation(name);
		if(ok){
			this.setName(name);
			new Thread(client).start();
			return ok;
		}else{
			return ok;
		}
	}

	/*
	 * Methods that deal with client
	 */
	
	private void sendMessage(MessageObject mo) throws IOException{
		client.sendMessage(mo);
	}
	
	public void closing() throws IOException{
			client.closeSocket(EndType.DISCONNECT);
	}
	
	private void handleMessage(MessageObject meob) throws IOException{
		//TODO create ENUM that tells what should be updated on GUI
		if(meob instanceof ChatMessage){
			chatReceived((ChatMessage) meob);
		}else if(meob instanceof PlayerMessage){
			playerInfoReceived((PlayerMessage) meob);
		}else if(meob instanceof ErrorMessage){
			handleErrorMessage((ErrorMessage) meob);
			meob = new ChatMessage("",false,"","");
		}
		messageReceived(meob);
	}
	
	/*
	 * Methods to deal with received Message
	 */
	
	private void chatReceived(ChatMessage chme){
		if(!chme.getSrceName().equals(name)){
			String tmp = "From <" + chme.getSrceName() + "> : ";
			tmp += chme.getText() + "\n";
			chatLog += tmp;
			if(chatLog.length() > chatMax){
				int exceed = chatLog.length() - chatMax;
				chatLog = chatLog.substring(exceed, chatLog.length());
			}
		}
	}
	
	private void playerInfoReceived(PlayerMessage plme){
		List<String> names = new ArrayList<>();
		for(String st: plme.getCodes()){
			String[] sts = st.split(":~><#");
			names.add(sts[0]);
		}
		otherPlayers = names;
		System.out.println(otherPlayers);
	}
	
	private void handleErrorMessage(ErrorMessage erme){
		ErrorType et = erme.getEt();
		switch(et){
		case CHAT_SRCE_DENIED:
			addTextToChatLog("You are not allowed chatting for now");
		break;
		case CHAT_DEST_DENIED: 
			addTextToChatLog("Destination is not allowed chatting for now");
		break;
		}
	}
	
	/*
	 * Methods to send message
	 */
	
	public void sendChat(ChatMessage chme) throws IOException{
		sendMessage(chme);
	}
	public void sendChat(String srceName, boolean toAll, String destName, String text) throws IOException{
		ChatMessage chme = new ChatMessage(srceName, toAll, destName, text);
		sendChat(chme);
	}
	
	/*
	 * About listener
	 */
	public void addMessageEventListener(MessageEventListener mel){
		listener = mel;
	}
	private void messageReceived(MessageObject meob) throws IOException{
		if(listener != null){
			listener.messageEvent(meob);
		}
	}
	
	/*
	 * miscellaneous
	 */
	/**
	 * This method is for adding text that is to notify player something on chatPane
	 * @param st text that you want to add
	 */
	public void addTextToChatLog(String st){
		chatLog += st + "\n";
	}
}
