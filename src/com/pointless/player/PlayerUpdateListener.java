package com.pointless.player;

import java.util.EventListener;

public interface PlayerUpdateListener extends EventListener {
	
	public void playerUpdateEvent(PlayerUpdate pu);

}
