package com.pointless.chat;

import java.util.HashMap;
import java.util.Map;

import com.pointless.message.ChatMessage;
import com.pointless.player.Player;

public class ChatFilter {
	private Map<Player, ChatFilterType> sourceFilter;
	private Map<Player, ChatFilterType> destinationFilter;

	public ChatFilter() {
		sourceFilter = new HashMap<Player, ChatFilterType>();
		destinationFilter = new HashMap<Player, ChatFilterType>();
	}

	
	public boolean verifyChat(ChatMessage chme) throws ChatIsLimitedException{
		return true;
	}
	
	public void changeSourceFilter(Player player, ChatFilterType cft){
		sourceFilter.put(player, cft);
		System.out.println("Message from " + player.getName() + " is now "+sourceFilter.get(player));
	}

	public void changeDestinationFilter(Player player, ChatFilterType cft){
		destinationFilter.put(player, cft);
		System.out.println("Message to " + player.getName() + " is now "+destinationFilter.get(player));
	}
}
