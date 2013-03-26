package com.pointless.gui;

import java.util.List;

import javax.swing.JPanel;

import com.pointless.player.Player;
import com.pointless.qm.Team;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OtherTeamInfoPane extends JPanel {
	
	private DestinationClickedListener destListener;

	/**
	 * Create the panel.
	 */
	public OtherTeamInfoPane() {
		setLayout(null);
	}
	
	public void refresh(List<Team> arg0){
		removeAll();
		for(int i=0; i<arg0.size(); i++){
			/*
			TeamCell tc1 = new TeamCell(arg0.get(i));
			tc1.addDestListener(new DestinationClickedListener(){
				public void destClicked(Player dest) {
					tellDestClicked(dest);
				}
			});
			tc1.setBounds(0, 36*i+2*i, 144, 40);
			add(tc1);
			*/
		}
		revalidate();
	}

	/**
	 * @param destListener the destListener to set
	 */
	public void addDestListener(DestinationClickedListener destListener) {
		this.destListener = destListener;
	}
	private void tellDestClicked(Player player){
		if(destListener != null){
			destListener.destClicked(player);
		}
	}
}
