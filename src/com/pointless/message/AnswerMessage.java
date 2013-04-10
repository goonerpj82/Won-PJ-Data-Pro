package com.pointless.message;

public class AnswerMessage extends MessageObject {

	private AnswerType at;
	private int index;
	
	public AnswerMessage(String srceName, AnswerType at, int index) {
		super(srceName);
		this.at = at;
		this.index = index;
	}
	
	public AnswerType getAnswerType(){
		return at;
	}
	
	public int getIndex(){
		return index;
	}

}
