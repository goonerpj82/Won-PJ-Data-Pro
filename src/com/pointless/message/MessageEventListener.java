package com.pointless.message;

import java.io.IOException;
import java.util.EventListener;


public interface MessageEventListener extends EventListener {

	public void messageEvent(MessageObject mo) throws IOException;
	
}
