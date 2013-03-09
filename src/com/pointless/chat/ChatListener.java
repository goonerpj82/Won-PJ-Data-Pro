package com.pointless.chat;

import java.util.EventListener;

public interface ChatListener extends EventListener {
	
	public void sendChat(Chat chat);

}
