package com.pointless.quiz;

import java.util.List;

public abstract class Quiz implements Comparable{
	protected String description;
	protected List<Answer> answers;
	protected QuizType quiztype;
	

	public Quiz(String description) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return points according to answer
	 */
	public abstract int checkAnswer(Answer answer);
	
	
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	/**
	 * @return the quiztype
	 */
	public QuizType getQuiztype() {
		return quiztype;
	}

	/**
	 * @param quiztype the quiztype to set
	 */
	public void setQuiztype(QuizType quiztype) {
		this.quiztype = quiztype;
	}

	/**
	 * 
	 */
	public int compareTo(Object arg0) {
		Quiz quiz = (Quiz) arg0;
		return quiztype.compareTo(quiz.getQuiztype());
	}
	
}