package com.pointless.gui;

import java.util.EventListener;

import com.pointless.comp.Player;

public interface DestinationClickedListener extends EventListener {
	
	public void destClicked(Player dest);

}
