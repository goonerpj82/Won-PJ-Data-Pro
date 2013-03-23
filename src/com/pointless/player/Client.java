package com.pointless.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.pointless.message.EndMessage;
import com.pointless.message.MessageObject;
import com.pointless.message.MessageEventListener;

/**
 * This class is to communicate with QuizMaster over TCP
 * Once client instance created, connection to server remains.
 * It is necessary to create new Thread for this instance
 * so this instance can receive message from server.
 * 
 * @author Won Lee
 * @version 1.0 b032222w
 * b032222w:	Basic mechanism + deal with EndMessage.
 *
 */
public class Client implements Runnable{
	private InetAddress servAddr;
	private Socket socket;
	private MessageEventListener sel;
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		//try {
			//socket.connect(socket.getRemoteSocketAddress());
		//} catch (IOException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
		//}
		while(true){
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				MessageObject mo = (MessageObject) ois.readObject();
				System.out.println("--- Message Received ---\n" + mo.toString());
				System.out.println("From: " + socket.getRemoteSocketAddress() + " To: " + socket.getLocalAddress());
				if(!(mo instanceof EndMessage)){
					messageEvent(mo);
				}else{
					//if EndMessage received, close the socket and tell listener about it.
					socket.close();
					messageEvent(mo);
					break;
				}
			} catch (SocketException e){
				//for the case that connection is broken
				break;
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param address of the server
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

	/**
	 * 
	 * @param mo
	 * @throws IOException
	 */
	public void sendMessage(MessageObject mo) throws IOException{
		System.out.println("Sending message");
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		mo.setHeader(socket);
		oos.writeObject(mo);
		oos.flush();
	}
	
	/**
	 * This method is to close socket.
	 * Before closing socket, this method send EndMessage
	 * so that remote host also close the socket.
	 * @throws IOException
	 */
	public void closeSocket() throws IOException{
		sendMessage(new EndMessage(""));
		socket.close();
	}
	
	public void addListener(MessageEventListener sel){
		this.sel = sel;
	}
	private void messageEvent(MessageObject mo){
		if(sel != null){
			sel.messageEvent(mo);
		}
	}

}
