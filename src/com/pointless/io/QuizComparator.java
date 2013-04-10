package com.pointless.io;

import java.util.Comparator;

import com.pointless.quiz.Quiz;

public class QuizComparator implements Comparator<Quiz> {

	public QuizComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Quiz arg0, Quiz arg1) {
		// TODO Auto-generated method stub
		return arg0.getQuiztype().compareTo(arg1.getQuiztype());
	}

}
