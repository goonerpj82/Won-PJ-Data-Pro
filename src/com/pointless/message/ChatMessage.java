package com.pointless.message;

/**
 * This class encapsulate necessary information for Chat
 * 
 * @author Won Lee
 * @version 0.1 b032414w
 *
 */
public class ChatMessage extends MessageObject {

	private boolean toAll;
	private String destName;
	private String text;
	
	/**
	 * 
	 * @param srceName
	 * @param destName
	 * @param text
	 */
	public ChatMessage(String srceName, boolean toAll, String destName, String text) {
		super(srceName);
		this.toAll = toAll;
		this.destName = destName;
		this.text = text;
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

	/**
	 * @return the destName
	 */
	public String getDestName() {
		return destName;
	}

	/**
	 * @param destName the destName to set
	 */
	public void setDestName(String destName) {
		this.destName = destName;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	public String toString(){
		return super.toString() + "\nChat from " + srceName + " to " + destName;
	}

}
