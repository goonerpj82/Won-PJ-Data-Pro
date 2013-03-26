package com.pointless.message;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * addressKey has to be set when the message is sent
 * 
 * @author Won
 *
 */
public class MessageObject implements Serializable{
	
	private static int messageIdBase = 0;
	protected int messageId;
	protected SocketAddress addressKey;
	protected String srceName;
	
	public MessageObject(String srceName){
		messageId = ++messageIdBase;
		this.srceName = srceName;
	}
	
	public SocketAddress getAddressKey(){
		return addressKey;
	}
	
	/**
	 * "/[IP address]:[port number]"
	 * This is used by question master to identify player 
	 * This method is supposed to be called when Client instance sends message.
	 * @param socket
	 */
	public void setAddressKey(Socket socket){
		addressKey = socket.getLocalSocketAddress();
	}
		
	/**
	 * 
	 * @return
	 */
	public String getSrceName(){
		return srceName;
	}
	
	public String toString(){
		return "This message ID is " + messageId + "\n" +
				"This message from: " + addressKey;
	}
}
