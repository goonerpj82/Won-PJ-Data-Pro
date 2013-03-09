package com.pointless.chat;

import java.util.Map;

import com.pointless.comp.*;

public class ChatFilter {
	private Map<Player, ChatFilterType> sourceFilter;
	private Map<Player, ChatFilterType> destinationFilter;

	public ChatFilter() {
		
	}

	
	public boolean verifyChat(Chat chat) throws ChatIsLimitedException{
		if(sourceFilter.get(chat.getSource()).equals(ChatFilterType.Deny)){
			return false;
		}
		if(destinationFilter.get(chat.getSource()).equals(ChatFilterType.Deny)){
			return false;
		}
		if(sourceFilter.get(chat.getSource()).equals(ChatFilterType.OnlyToTeam) ||
				destinationFilter.get(chat.getDestination()).equals(ChatFilterType.OnlyToTeam)){
			throw new ChatIsLimitedException();
		}
		return true;
	}
	
	public void changeSourceFilter(Player player, ChatFilterType cft){
		sourceFilter.put(player, cft);
	}

	public void changeDestinationFilter(Player player, ChatFilterType cft){
		destinationFilter.put(player, cft);
	}
}
