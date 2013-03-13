package com.pointless.comp;

import java.io.Serializable;
import java.util.List;

import com.pointless.quiz.Answer;
import com.pointless.quiz.Quiz;

public class Player implements Serializable{
	private int id;
	private String name;
	private boolean myTurn;
	private Quiz givenQuiz;
	private List<Player> otherPlayers;
	private PlayerAnswerListener paListener;

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
	public List<Player> getOtherPlayers() {
		return otherPlayers;
	}

	/**
	 * @param otherPlayers the otherPlayers to set
	 */
	public void setOtherPlayers(List<Player> otherPlayers) {
		this.otherPlayers = otherPlayers;
	}

	public void addPlayerAnswerListener(PlayerAnswerListener paListener){
		this.paListener = paListener;
	}
	public void answer(Answer palyerAnswer){
		if(paListener != null){
			paListener.playerAnswered(palyerAnswer);
		}
	}
}
