package com.pointless.message;

public class EndMessage extends MessageObject {
	
	private EndType et;

	public EndMessage(String srceName, EndType et) {
	//public EndMessage(String srceName) {
		super(srceName);
		this.et = et;
	}

}
