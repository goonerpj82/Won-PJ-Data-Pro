package com.pointless.gui;

import java.util.EventListener;

import com.pointless.quiz.Answer;

public interface GuiAnswerListener extends EventListener {

	public void guiAnswered(Answer guiAnswer);
	
}
