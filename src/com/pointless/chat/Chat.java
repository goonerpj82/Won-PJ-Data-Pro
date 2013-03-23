package com.pointless.chat;

import com.pointless.player.Player;

/**
 * This class the structure of Chat.
 * @author Won Lee
 *
 */
public class Chat {
	private boolean toAll;
	private Player source;
	private Player destination;
	private String message;

	/**
	 * 
	 * @param source player instance
	 * @param destination player instance
	 * @param message 
	 * @param toAll true for sending message to all players
	 */
	public Chat(Player source, Player destination, String message, boolean toAll) {
		super();
		this.toAll = toAll;
		this.source = source;
		this.destination = destination;
		this.message = message;
	}
	
	/**
	 * @return the source
	 */
	public Player getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Player source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public Player getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Player destination) {
		this.destination = destination;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the toAll
	 */
	public boolean isToAll() {
		return toAll;
	}

	/**
	 * @param toAll the toAll to set
	 */
	public void setToAll(boolean toAll) {
		this.toAll = toAll;
	}
	
}
