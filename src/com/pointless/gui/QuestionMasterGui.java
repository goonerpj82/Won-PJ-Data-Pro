package com.pointless.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatFilter;
import com.pointless.chat.ChatFilterType;
import com.pointless.chat.ChatIsLimitedException;
import com.pointless.chat.ChatListener;
import com.pointless.comp.Player;
import com.pointless.comp.QuestionMaster;
import com.pointless.comp.Team;
import com.pointless.quiz.Answer;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class hold QuestionMaster instance and get necessary information for PlayerGui from it.
 * All method to proceed the game will be implemented in QuestionMaster Class and this class.
 * 
 * 
 * @author Won Lee
 *
 */
public class QuestionMasterGui extends JFrame {

	private JPanel contentPane;
	private Map<Player,PlayerGui> mapOfPP = new HashMap<Player,PlayerGui>();
	private QuestionMaster qm = new QuestionMaster();
	private JTextField txtAddPlayer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuestionMasterGui frame = new QuestionMasterGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QuestionMasterGui() {		
		
		System.out.println(qm.getQuizList().size());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtAddPlayer = new JTextField();
		txtAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField txt = (JTextField) arg0.getSource();
				addPlayer(new Player(txt.getText()));
				txt.setText("");
			}
		});
		txtAddPlayer.setText("Add Player");
		txtAddPlayer.setBounds(10, 11, 86, 20);
		contentPane.add(txtAddPlayer);
		txtAddPlayer.setColumns(10);
	}
	
	private void addPlayer(Player player){
		qm.addPlayer(player);
		PlayerGui pGui = new PlayerGui(player);
		pGui.setVisible(true);
		mapOfPP.put(player, pGui);
	}
	
	private void startGame(List<Player> players, List<Team> teams){
		
	}
}
