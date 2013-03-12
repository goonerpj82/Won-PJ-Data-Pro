package com.pointless.comp;

import java.io.Serializable;

public class Player implements Serializable, Comparable{
	private int id;
	private String name;

	public Player(int id, String name) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
