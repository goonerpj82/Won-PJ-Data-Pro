package com.pointless.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client implements Runnable{
	private InetAddress servAddr;
	private Socket socket;
	private ServerEventListener sel;
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			socket.connect(socket.getRemoteSocketAddress());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				MessageObject mo = (MessageObject) ois.readObject();
				System.out.println("--- Message Received ---\n" + mo.toString());
				System.out.println("From: " + socket.getRemoteSocketAddress() + " To: " + socket.getLocalAddress());
				serverEvent(mo);
			} catch (SocketException e){
				break;
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public Client(String address) throws IOException{
		servAddr = InetAddress.getByName(address);
		socket = new Socket(servAddr, 53346);
	}
	
	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public Client(Socket socket){
		this.socket = socket;
	}

	public void sendMessage(MessageObject mo) throws IOException{
		System.out.println("Sending message");
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		mo.setHeader(socket);
		oos.writeObject(mo);
		oos.flush();
	}
	
	public void addListener(ServerEventListener sel){
		this.sel = sel;
	}
	private void serverEvent(MessageObject mo){
		if(sel != null){
			sel.serverEvent(mo);
		}
	}

}
