package com.pointless.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.omg.CORBA_2_3.portable.OutputStream;

/**
 * 
 * @author Won Lee
 * @version 1.0 b032222w
 * b032222w:	Basic mechanism.
 *
 */
public class Server implements Runnable{
	private ServerEventListener sel;
	private List<Client> clients = new ArrayList<Client>();
			
	public Server(){
	}

	public void run() {
		try {
			int servPort = 53346;
			ServerSocket servSock = new ServerSocket(servPort);
			
			while(true){
				System.out.println("Waiting for client ...");
				Socket playerSocket = servSock.accept();
				System.out.println("Client: " + playerSocket.toString() + " was connected");
				
				Client newConnection = new Client(playerSocket);
				newConnection.addListener(new ServerEventListener(){
					public void serverEvent(MessageObject mo) {
						messageFromClient(mo);
					}
				});
				Thread thread = new Thread(newConnection);
				clients.add(newConnection);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addListener(ServerEventListener sel){
		this.sel = sel;
	}
	private void messageFromClient(MessageObject mo){
		System.out.println(mo.printHeader());
		if(sel != null){
			sel.serverEvent(mo);
		}
	}
	
	public void sendMessage(int i) throws IOException{
		clients.get(i).sendMessage(new MessageObject("Server"));
	}
}
