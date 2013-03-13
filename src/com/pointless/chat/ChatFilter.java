package com.pointless.chat;

import java.util.HashMap;
import java.util.Map;

import com.pointless.comp.*;

public class ChatFilter {
	private Map<Player, ChatFilterType> sourceFilter;
	private Map<Player, ChatFilterType> destinationFilter;

	public ChatFilter() {
		sourceFilter = new HashMap<Player, ChatFilterType>();
		destinationFilter = new HashMap<Player, ChatFilterType>();
	}

	
	public boolean verifyChat(Chat chat) throws ChatIsLimitedException{
		if(sourceFilter.get(chat.getSource()).equals(ChatFilterType.Deny)){
			System.out.println("Message from " + chat.getSource().getName() + " is not allowed.");
			return false;
		}
		if(destinationFilter.get(chat.getSource()).equals(ChatFilterType.Deny)){
			System.out.println("Message to " + chat.getDestination().getName() + " is not allowed.");
			return false;
		}
		//if(sourceFilter.get(chat.getSource()).equals(ChatFilterType.OnlyToTeam) ||
		//		destinationFilter.get(chat.getDestination()).equals(ChatFilterType.OnlyToTeam)){
		//	System.out.println("Source or Destination Player is only allow to talk with team mate");
		//	throw new ChatIsLimitedException();
		//}
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
