package com.pointless.message;

import com.pointless.quiz.Quiz;

/**
 * This message is used to give player with quiz.
 * We have to make new class that handle quiz that doesn't contain score for each answer
 * @author Won Lee
 * @version	b041011w
 * b041011w:	basic done but handling quiz have to be improved.
 *
 */
public class QuizMessage extends MessageObject {
	
	private QuizMType qt;
	private Quiz quiz;

	public QuizMessage(String srceName, QuizMType qt, Quiz quiz) {
		super(srceName);
		this.qt = qt;
		this.quiz = quiz;
	}
	
	public QuizMType getQuizType(){
		return qt;
	}
	
	public Quiz getQuiz(){
		return quiz;
	}

}
