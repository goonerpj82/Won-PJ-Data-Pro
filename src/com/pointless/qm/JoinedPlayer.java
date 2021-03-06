package com.pointless.qm;

/**
 * This class hold information of players.
 * This class is handled by QuestionMaster class, not Player class
 * @author Won Lee
 * @version b032608w
 * b032608w:	basic done.
 *
 */
public class JoinedPlayer {

	private String name;
	private String addressKey;
	private int score;
	
	
	public JoinedPlayer(String name, String addressKey) {
		this.name = name;
		this.addressKey = addressKey;
		score = 0;
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
	 * @return the addressKey
	 */
	public String getAddressKey() {
		return addressKey;
	}
	/**
	 * @param addressKey the addressKey to set
	 */
	public void setAddressKey(String addressKey) {
		this.addressKey = addressKey;
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
	
	public void addScore(int addedScore){
		score += addedScore;
	}

}
