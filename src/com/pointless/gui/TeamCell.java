package com.pointless.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.pointless.player.Player;
import com.pointless.qm.Team;

public class TeamCell extends JPanel {
	
	private int teamId;
	private Player player1;
	private Player player2;
	private DestinationClickedListener destListener;
	private JButton btnPlayer1;

	/**
	 * Create the panel.
	 */
	public TeamCell(Team team) {
		setLayout(null);
		
		JLabel lblScore = new JLabel("0");
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(0, 0, 36, 36);
		add(lblScore);
		
		try{
			player1 = team.getPlayers().get(0);
			btnPlayer1 = new JButton(player1.getName());
			btnPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 10));
			btnPlayer1.setBounds(36, 0, 108, 18);
			btnPlayer1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					destClicked(player1);
				}
			});
			add(btnPlayer1);
			
			player2 = team.getPlayers().get(1);
			JButton btnPlayer2 = new JButton(player2.getName());
			btnPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 10));
			btnPlayer2.setBounds(36, 18, 108, 18);
			btnPlayer2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					destClicked(player2);
				}
			});
			add(btnPlayer2);
		}catch (IndexOutOfBoundsException e){
			//catch NullPointer if there is no second player.
			btnPlayer1.setSize(btnPlayer1.getWidth(), btnPlayer1.getHeight()+18);
			e.printStackTrace();
		}
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
