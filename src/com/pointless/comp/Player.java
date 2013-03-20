package com.pointless.comp;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatListener;
import com.pointless.quiz.Answer;
import com.pointless.quiz.Quiz;

public class Player extends Observable implements Serializable{
	private int id;
	private String name;
	private boolean myTurn;
	private Quiz givenQuiz;
	private List<Team> teams;
	private ChatListener chatListener;

	public Player(String name) {
		this.name = name;
	}
	
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
	 * @return the otherPlayers
	 */
	public List<Team> getTeams() {
		return teams;
	}
	
	public void addChatListener(ChatListener chatListener){
		this.chatListener = chatListener;
	}
	public void receiveChat(Chat chat){
		System.out.println("Chat is received at Player");
		if(chatListener != null){
			chatListener.chatEvent(chat);
		}
	}

	/**
	 * @param otherPlayers the otherPlayers to set
	 */
	public void setOtherPlayers(List<Team> teams) {
		this.teams = teams;
	}
	public void answerQuestion(Answer answer){
		notifyObservers(answer);
	}

	public void passChatToMaster(Chat chat){
		notifyObservers(chat);
	}
	public void closing(){
		this.notifyObservers("Bye Bye");
	}

	/* (non-Javadoc)
	 * @see java.util.Observable#notifyObservers(java.lang.Object)
	 */
	@Override
	public void notifyObservers(Object arg) {
		System.out.println("Notifying "+arg.toString());
		setChanged();
		super.notifyObservers(arg);
	}
}
