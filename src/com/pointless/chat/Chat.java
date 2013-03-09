package com.pointless.chat;

import com.pointless.comp.Player;

public class Chat {
	private Player source;
	private Player destination;
	private String message;

	public Chat(Player source, Player destination, String message) {
		super();
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

}
