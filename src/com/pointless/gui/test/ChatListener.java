package com.pointless.gui.test;

import java.util.EventListener;

public interface ChatListener extends EventListener {
	
	public void sendText(Chat chat);

}
