package com.pointless.player;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pointless.chat.Chat;
import com.pointless.chat.ChatListener;
import com.pointless.gui.ChatPane;
import com.pointless.gui.DestinationClickedListener;
import com.pointless.gui.GuiAnswerListener;
import com.pointless.gui.MainDisplayPane;
import com.pointless.gui.OtherTeamInfoPane;
import com.pointless.message.Client;
import com.pointless.qm.Team;
import com.pointless.quiz.Answer;

import javax.swing.JLayeredPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;

/**
 * All necessary information to generate GUi is given by QuestionMasterGui instance.
 * Encapsulate chat from ChatPane, playerAnswer from QuestionPane for QuestionMasterGui
 * which means encapsulate them for QuestionMaster class.
 * @author Won
 *
 */
public class PlayerGui extends JFrame {
	
	private Player player;
	private ChatPane chatPane;
	private OtherTeamInfoPane otip;
	private JPanel contentPane;
	private MainDisplayPane mainDisplayPane;
	private JMenuBar menuBar;
	private JTextField txfQm;
	private JTextField txfName;
	private JButton btnConnect;
	private JPanel loginPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGui frame = new PlayerGui(null);
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
	public PlayerGui(Player constPlayer) {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Hey");
				player.closing();
			}
		});
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/*
		if(constPlayer == null){
			String name = JOptionPane.showInputDialog("Enter Player Name");
			constPlayer = new Player(name);
		}
		*
		
		player = constPlayer;
		player.addChatListener(new ChatListener(){
			public void chatEvent(Chat chat) {
				chatPane.receiveMessage(chat);
			}
			public void chatEvent(Player dest, String message, boolean toAll) {}
		});

		this.setTitle("Player <" + player.getName() + ">");
		*/
		
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 20, 494, 352);
		contentPane.add(layeredPane);
		
		chatPane = new ChatPane();
		chatPane.setBounds(144, 200, 350, 150);
		chatPane.addChatListener(new ChatListener(){
			public void chatEvent(Chat chat) {}
			public void chatEvent(Player dest, String message, boolean toAll) {
				System.out.println("PlayerGui heard ChatEvent");
				Chat chat = new Chat(player, dest, message, toAll);
				player.passChatToMaster(chat);
			}
		});
		layeredPane.add(chatPane);
		
		otip = new OtherTeamInfoPane();
		otip.setBounds(0, 200, 145, 150);
		otip.addDestListener(new DestinationClickedListener(){
			public void destClicked(Player dest) {
				chatPane.changeDest(dest);
				//relayMessage(new Chat(player, player, "Clicked", false));
			}
		});
		layeredPane.add(otip);
		
		mainDisplayPane = new MainDisplayPane();
		mainDisplayPane.setBounds(0, 0, 494, 200);
		mainDisplayPane.addGuiAnswerListener(new GuiAnswerListener(){
			public void guiAnswered(Answer guiAnswer) {
				
			}
		});
		layeredPane.add(mainDisplayPane);
		
		/*
		 * Code for Login Panel. This panel is displayed at first.
		 * Enter server address and player's Name.
		 * Connecting server successfully and player name is available,
		 * Login Panel will be removed.
		 */
		loginPane = new JPanel();
		layeredPane.setLayer(loginPane, 10);
		loginPane.setBounds(0, 0, 494, 350);
		layeredPane.add(loginPane);
		FlowLayout fl_loginPane = new FlowLayout(FlowLayout.CENTER, 5, 5);
		loginPane.setLayout(fl_loginPane);
		
		JLabel lblQuizmasterAddress = new JLabel("QuizMaster Address");
		loginPane.add(lblQuizmasterAddress);
		
		txfQm = new JTextField();
		loginPane.add(txfQm);
		txfQm.setColumns(10);
		
		JLabel lblYourGameName = new JLabel("Your Game Name");
		loginPane.add(lblYourGameName);
		
		txfName = new JTextField();
		loginPane.add(txfName);
		txfName.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Clicked");
				player = new Player();
				try {
					boolean ok = player.joinGame(txfQm.getText(), txfName.getText());
					System.out.println("Success? => " + ok);
					if(ok){
						loginPane.setVisible(false);
					}else{
						txfName.setText("");
					}
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					txfQm.setText("");
					e.printStackTrace();
				}
			}
		});
		loginPane.add(btnConnect);
		
		//End of Login Pane Code.
		
		/*
		 * Code for Menu bar
		 */
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 494, 21);
		contentPane.add(menuBar);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void relayMessage(Chat chat){
		System.out.println("Relaying Message at PlayerGui");
		chatPane.receiveMessage(chat);
	}
	
	public void showNotification(String message){
		
	}
	
	public void updateTeamInfo(List<Team> teams){
		
	}
}
