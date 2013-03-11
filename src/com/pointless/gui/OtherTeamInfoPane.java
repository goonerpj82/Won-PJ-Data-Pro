package com.pointless.gui;

import java.util.List;

import javax.swing.JPanel;

import com.pointless.comp.Player;
import com.pointless.comp.Team;

public class OtherTeamInfoPane extends JPanel {
	
	private DestinationClickedListener destListener;

	/**
	 * Create the panel.
	 */
	public OtherTeamInfoPane(List<Team> teams) {

	}

	/**
	 * @param destListener the destListener to set
	 */
	public void addDestListener(DestinationClickedListener destListener) {
		this.destListener = destListener;
	}
	private void destClicked(Player player){
		if(destListener != null){
			destListener.destClicked(player);
		}
	}

}
