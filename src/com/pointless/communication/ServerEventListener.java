package com.pointless.communication;

import java.util.EventListener;

public interface ServerEventListener extends EventListener {

	public void serverEvent(MessageObject mo);
	
}
