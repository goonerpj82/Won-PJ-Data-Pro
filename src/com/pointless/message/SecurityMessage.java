package com.pointless.message;

public class SecurityMessage extends MessageObject {

	private SecurityType st;
	private int p;
	private int g;
	private int x;
	
	/**
	 * Constructor for server
	 * @param srceName
	 * @param st
	 * @param p
	 * @param g
	 * @param x
	 */
	public SecurityMessage(String srceName, SecurityType st, int p, int g) {
		super(srceName);
		this.st = st;
		this.p = p;
		this.g = g;
	}
	
	/**
	 * Constructor for player
	 * @param srceName
	 * @param st
	 * @param x
	 */
	public SecurityMessage(String srceName, SecurityType st, int x) {
		super(srceName);
		this.x = x;
	}	

}
