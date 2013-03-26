package com.pointless.qm

import com.pointless.io.Client;
import com.pointless.message.FirstMessage
import com.pointless.message.FirstType;
import com.pointless.message.MessageEventListener;
import com.pointless.message.MessageObject;

class TestTCPofQustionMaster {

	static main(args) {
		//*
		Client client = new Client("127.0.0.1");
		client.addListener(new MessageEventListener(){
			public void messageEvent(MessageObject mo) {
				//println "mo.toString()";
			}
		});
		def th = new Thread(client).start();
		def fm = new FirstMessage("Won Lee 2", FirstType.REQUEST);
		client.sendMessage(fm);
		//client.closeSocket();
		/*/
		
		/*
		List<String> strings = new ArrayList<>();
		strings.addAll(["A","B","C","D","E","F"]);
		println strings;
		for(String st: strings){
			if(st.equals("B")){
				println st + "found";
				strings.remove(st);
			}
		}
		*/
	}

}
