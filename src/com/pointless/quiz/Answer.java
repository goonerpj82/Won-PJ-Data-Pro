package com.pointless.quiz;

public class Answer {
	private String phrase;
	private int point;

	public Answer(String phrase, int point) {
		// TODO Auto-generated constructor stub
		this.phrase = phrase;
		this.point = point;
	}

	/**
	 * @return the phrase
	 */
	public String getPhrase() {
		return phrase;
	}

	/**
	 * @param phrase the phrase to set
	 */
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
	}

	public String toString(){
		return "Phrase: " + phrase + "  Point: " + point;
	}
}
