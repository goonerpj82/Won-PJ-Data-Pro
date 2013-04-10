package com.pointless.qm;

import java.util.Comparator;

public class SortByScore implements Comparator<JoinedPlayer> {

	@Override
	public int compare(JoinedPlayer arg0, JoinedPlayer arg1) {
		// TODO Auto-generated method stub
		return arg0.getScore() - arg1.getScore();
	}

}
