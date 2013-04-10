package com.pointless.message;

public class ErrorMessage extends MessageObject {

	private ErrorType et;
	
	public ErrorMessage(String srceName, ErrorType et) {
		super(srceName);
		this.et = et;
	}

	/**
	 * @return the et
	 */
	public ErrorType getEt() {
		return et;
	}

}
