package com.pointless.player;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import com.pointless.gui.GuiAnswerListener;
import com.pointless.gui.MainDisplayPane;
import com.pointless.gui.OtherTeamInfoPane;
import com.pointless.io.Client;
import com.pointless.message.ChatMessage;
import com.pointless.message.MessageEventListener;
import com.pointless.message.MessageObject;
import com.pointless.message.PlayerMessage;
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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

/**
 * All necessary information to generate GUi is given by QuestionMasterGui instance.
 * Encapsulate chat from ChatPane, playerAnswer from QuestionPane for QuestionMasterGui
 * which means encapsulate them for QuestionMaster class.
 * @author Won
 * @version 0.3 b032608w
 * b032608w:	Integrate chatPanel into PlayerGui.
 *
 */
public class PlayerGui extends JFrame {
	
	private Player player;
	private OtherTeamInfoPane otip;
	private JPanel contentPane;
	private MainDisplayPane mainDisplayPane;
	private JMenuBar menuBar;
	//variables for chat
	private JTextArea textArea;
	private JComboBox destBox;
	private JTextField textField;
	private JCheckBox cbxToAll;
	//variables for login
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
				try {
					player.closing();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		});
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 20, 494, 352);
		contentPane.add(layeredPane);
		
		otip = new OtherTeamInfoPane();
		layeredPane.setLayer(otip, 5);
		otip.setBounds(0, 200, 145, 150);
		layeredPane.add(otip);
		
		mainDisplayPane = new MainDisplayPane();
		layeredPane.setLayer(mainDisplayPane, 5);
		mainDisplayPane.setBounds(0, 0, 494, 200);
		mainDisplayPane.addGuiAnswerListener(new GuiAnswerListener(){
			public void guiAnswered(Answer guiAnswer) {
				
			}
		});
		layeredPane.add(mainDisplayPane);
		
		/*
		 * CHAT, codes for chat function
		 */
		
		JPanel chatPane = new JPanel();
		chatPane.setBounds(144, 200, 350, 150);
		layeredPane.add(chatPane);
		chatPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 350, 130);
		chatPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		destBox = new JComboBox();
		destBox.setBounds(0, 130, 60, 20);
		chatPane.add(destBox);
		
		cbxToAll = new JCheckBox("To All");
		cbxToAll.setBounds(295, 130, 51, 20);
		chatPane.add(cbxToAll);

		textField = new JTextField();
		textField.setBounds(60, 130, 235, 20);
		textField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean toAll = cbxToAll.isSelected();
					String destName = (toAll)? "All":destBox.getSelectedItem().toString();
					String text = textField.getText();
					player.sendChat(player.getName(), toAll, destName, text);
					player.addTextToChatLog("To <" + destName + "> : " + text);
					textArea.setText(player.getChatLog());
					textField.setText("");
				} catch (IOException e) {
					player.addTextToChatLog("!!! System couldn't send chat to "
							+ destBox.getSelectedItem().toString() + "!!!");
					e.printStackTrace();
				}
			}
		});
		chatPane.add(textField);
		textField.setColumns(10);
		

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
				player.addMessageEventListener(new MessageEventListener(){
					public void messageEvent(MessageObject mo)
							throws IOException {
						updateDisplay(mo);
					}
				});
				try {
					boolean ok = player.joinGame(txfQm.getText(), txfName.getText());
					System.out.println("Success? => " + ok);
					if(ok){
						setTitle(txfName.getText());
						textArea.setText(player.getChatLog());
						loginPane.setVisible(false);
					}else{
						txfName.setText("");
					}
				} catch (ClassNotFoundException | IOException e) {
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
	
	public void updateDisplay(MessageObject mo){
		if(mo instanceof PlayerMessage){
			destBox.setModel(new DefaultComboBoxModel(player.getOtherPlayers().toArray()));
		}if(mo instanceof ChatMessage){
			textArea.setText(player.getChatLog());
		}
	}
	
	public void showNotification(String message){
		
	}
	
	public void updateTeamInfo(List<Team> teams){
		
	}
}
