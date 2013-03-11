package com.pointless.chat;

import java.util.EventListener;

import com.pointless.comp.Player;

/**
 * 2 phases. First sendChat() is for listener in PlayerGui, second one is for listener in ChatPane
 * @author Won
 *
 */
public interface ChatListener extends EventListener {
	
	public void sendChat(Chat chat);

	public void sendChat(Player dest, String message, boolean toAll);
	
}
