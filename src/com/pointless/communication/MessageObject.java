package com.pointless.communication;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MessageObject implements Serializable{
	
	private static int messageIdBase = 0;
	private int messageId;
	private InetAddress destAddress;
	private InetAddress srceAddress;
	private int srcePort;
	private String srceName;
	
	public MessageObject(String srceName){
		messageId = ++messageIdBase;
		this.srceName = srceName;
	}
	public MessageObject(String srceName, Socket socket){
		messageId = ++messageIdBase;
		this.srceName = srceName;
		setHeader(socket);
	}
	
	public void setHeader(Socket socket){
		destAddress = socket.getInetAddress();
		srceAddress = socket.getLocalAddress();
		srcePort = socket.getLocalPort();
	}
		
	/**
	 * @return the destAddress
	 */
	public InetAddress getDestAddress() {
		return destAddress;
	}
	/**
	 * @return the srceAddress
	 */
	public InetAddress getSrceAddress() {
		return srceAddress;
	}
	/**
	 * @return the srcePort
	 */
	public int getSrcePort() {
		return srcePort;
	}
	
	public String toString(){
		return "This message ID is " + messageId + "\n" +
				"This message from: " + srceAddress.getHostAddress() + ":" +
						"" + srcePort + " to " + destAddress.getHostAddress();
	}
}
