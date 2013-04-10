package com.pointless.chat;

import java.util.HashMap;
import java.util.Map;

import com.pointless.message.ChatMessage;
import com.pointless.message.ErrorType;
import com.pointless.player.Player;

public class ChatFilter {
	private Map<String, ChatFilterType> filter;

	public ChatFilter() {
		filter = new HashMap<>();
	}

	
	public boolean verifyChat(ChatMessage chme) throws ChatIsLimitedException{
		ChatFilterType cft = filter.get(chme.getSrceName());
		if(cft == ChatFilterType.Deny){
			return false;
		}
		cft = filter.get(chme.getDestName());
		if(cft == ChatFilterType.Deny){
			return false;
		}
		return true;
	}
	
	/**
	 * Call this method only after verifyChat and get false from it.
	 * @param chme
	 * @return
	 */
	public ErrorType checkError(ChatMessage chme){
		if(filter.get(chme.getSrceName()).equals(ChatFilterType.Deny)){
			return ErrorType.CHAT_SRCE_DENIED;
		}else{
			return ErrorType.CHAT_DEST_DENIED;
		}
	}
	
	public void changeFilter(String name, ChatFilterType cft){
		filter.put(name, cft);
		System.out.println("Message from " + name + " is now "+filter.get(name));
	}
}
