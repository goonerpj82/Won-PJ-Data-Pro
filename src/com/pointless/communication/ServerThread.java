package com.pointless.communication;

import java.net.Socket;

public class ServerThread implements Runnable {
	private Socket socket;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ServerThread(Socket socket){
		this.socket = socket;
	}

}
