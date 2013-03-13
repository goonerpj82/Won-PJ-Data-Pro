package com.pointless.comp;

import java.util.EventListener;

import com.pointless.quiz.Answer;

public interface PlayerAnswerListener extends EventListener {
	
	public void playerAnswered(Answer palyerAnswer);

}
