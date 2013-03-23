package com.pointless.message;

import java.util.EventListener;


public interface MessageEventListener extends EventListener {

	public void messageEvent(MessageObject mo);
	
}
