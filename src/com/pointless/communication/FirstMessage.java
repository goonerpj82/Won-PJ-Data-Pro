package com.pointless.communication;

import java.net.Socket;

public class FirstMessage extends MessageObject {
	
	private int tmpPort;
	
	public FirstMessage(String srceName, int tmpPort){
		super(srceName);
		this.tmpPort = tmpPort;
	}
	
	public FirstMessage(String srceName, Socket socket, int tmpPort){
		super(srceName, socket);
		this.tmpPort = tmpPort;
	}
	
	public int getTmpPort(){
		return tmpPort;
	}
	
	public void setTmpPort(int tmpPort){
		this.tmpPort = tmpPort;
	}
}
