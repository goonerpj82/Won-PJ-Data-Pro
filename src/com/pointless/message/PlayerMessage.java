package com.pointless.message;

import java.util.List;

public class PlayerMessage extends MessageObject {

	List<String> codes;
	
	public PlayerMessage(String srceName, List<String> codes) {
		super(srceName);
		this.codes = codes;
	}

	/**
	 * @return the codes
	 */
	public List<String> getCodes() {
		return codes;
	}

	/**
	 * @param codes the codes to set
	 */
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
	
	

}
