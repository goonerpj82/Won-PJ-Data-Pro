package com.pointless.quiz;

import java.io.Serializable;

public class Answer implements Serializable{
	//ID will be used when we separate Player and Questionmaster
	//When they communicate over TCP
	private static int idBase = 0;
	private int id;
	private String phrase;
	private int point;

	public Answer(String phrase, int point) {
		id = ++idBase;
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
