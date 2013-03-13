package com.pointless.comp;

import java.io.File;
import java.util.List;

import com.pointless.io.QuestionLoader;
import com.pointless.quiz.Quiz;

public class QuestionMaster {
	private List<Player> players;
	private List<Team> teams;
	private List<Quiz> quizList;
	private int currentRound;
	
	public QuestionMaster(){
		quizList = QuestionLoader.load(new File("Quizes"));
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
		players.add(player);
	}
	
	public void createTeam(Player player1){
		teams.add(new Team(player1));
	}
	public void createTeam(Player player1, Player player2){
		teams.add(new Team(player1, player2));
	}

}
