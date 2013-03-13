package com.pointless.comp;

import java.util.ArrayList;
import java.util.List;

public class Team {
	
	private int score = 0;
	private List<Player> players;
	
	public Team(Player player1){
		players = new ArrayList<Player>();
		players.add(player1);
	}
	public Team(Player player1, Player player2){
		players = new ArrayList<Player>();
		players.add(player1);
		if(player2 != null){
			players.add(player2);
		}
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
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
	
	

}
