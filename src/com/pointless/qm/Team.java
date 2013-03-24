package com.pointless.qm;

import java.util.ArrayList;
import java.util.List;

import com.pointless.player.Player;

public class Team {
	
	private List<JoinedPlayer> players;
	
	public Team(JoinedPlayer player1){
		players = new ArrayList<>();
		players.add(player1);
	}
	public Team(JoinedPlayer player1, JoinedPlayer player2){
		players = new ArrayList<>();
		players.add(player1);
		if(player2 != null){
			players.add(player2);
		}
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		int score = 0;
		for(JoinedPlayer jp: players){
			score += jp.getScore();
		}
		return score;
	}

	/**
	 * @return the players
	 */
	public List<JoinedPlayer> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<JoinedPlayer> players) {
		this.players = players;
	}
	
	public void addPlayerToTeam(JoinedPlayer player){
		if(players.size() < 2){
			players.add(player);
		}else{
			//throw new TeamSizeException();
		}
	}

}
