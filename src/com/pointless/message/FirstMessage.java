package com.pointless.message;

import java.net.Socket;

/**
 * This message is used by Player asking server if the player name is available.
 * There is two phase, REQUEST and response (CONFIRM or REJECT)
 * @author Won Lee
 * @version 1.0 b032412w
 * b032412w:	Basic Done.
 *
 */
public class FirstMessage extends MessageObject {
	
	private FirstType ft;
	
	public FirstMessage(String srceName, FirstType ft){
		super(srceName);
		this.ft = ft;
	}

	/**
	 * @return the ft
	 */
	public FirstType getFt() {
		return ft;
	}

	/**
	 * @param ft the ft to set
	 */
	public void setFt(FirstType ft) {
		this.ft = ft;
	}
	
	public String toString(){
		return super.toString() + "\n FirstType: " + ft;
	}
}
