package com.pointless.quiz;

import java.io.Serializable;
import java.util.List;

public abstract class Quiz implements Serializable{
	
	protected static int idBase = 0;
	protected int id;
	protected String title;
	protected String description;
	protected String category;
	protected List<Answer> answers;
	protected QuizType quiztype;
	

	public Quiz(String title, String description, String category, List<Answer> answers) {
		// TODO Auto-generated constructor stub
		id = ++idBase;
		this.title = title;
		this.description = description;
		this.category = category;
		this.answers = answers;
	}

	/**
	 * 
	 * @return points according to answer
	 */
	public abstract int checkAnswer(Answer answer);

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

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
