package com.pointless.qm;

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

import com.pointless.message.Client;
import com.pointless.message.MessageObject;
import com.pointless.message.MessageEventListener;

/**
 * 
 * @author Won Lee
 * @version 1.0 b032222w
 * b032222w:	Basic mechanism.
 *
 */
public class Server implements Runnable{
	private MessageEventListener sel;
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
				newConnection.addListener(new MessageEventListener(){
					public void messageEvent(MessageObject mo) throws IOException {
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
	
	public void addListener(MessageEventListener sel){
		this.sel = sel;
	}
	private void messageFromClient(MessageObject mo) throws IOException{
		System.out.println(mo.getAddressKey());
		if(sel != null){
			sel.messageEvent(mo);
		}
	}
	
	public void sendMessage(int i) throws IOException{
		clients.get(i).sendMessage(new MessageObject("Server"));
	}
}
