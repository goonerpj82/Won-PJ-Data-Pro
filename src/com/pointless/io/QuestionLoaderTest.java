package com.pointless.io;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.pointless.quiz.Answer;
import com.pointless.quiz.Quiz;

public class QuestionLoaderTest extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuestionLoaderTest frame = new QuestionLoaderTest();
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
	public QuestionLoaderTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setCurrentDirectory(new File("Quizes"));
		jfc.showOpenDialog(new JFrame());
		File file = jfc.getSelectedFile();
		
		List<Quiz> quizList = QuestionLoader.load(file);
		
		String st = "";
		for(Quiz quiz: quizList){
			st += quiz.toString()+"\n";
			for(Answer ans: quiz.getAnswers()){
				st += " - "+ans.toString()+"\n";
			}
		}
		
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(st);
		scrollPane.setViewportView(textPane);
	}

}
