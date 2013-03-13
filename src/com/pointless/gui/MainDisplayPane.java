package com.pointless.gui;

import javax.swing.JPanel;

import com.pointless.quiz.Answer;

public class MainDisplayPane extends JPanel {
	private GuiAnswerListener guiAnListener;

	/**
	 * Create the panel.
	 */
	public MainDisplayPane() {

	}

	public void addGuiAnswerListener(GuiAnswerListener guiAnListener){
		this.guiAnListener = guiAnListener;
	}
	private void answer(Answer answer){
		if(guiAnListener != null){
			guiAnListener.guiAnswered(answer);
		}
	}
}
