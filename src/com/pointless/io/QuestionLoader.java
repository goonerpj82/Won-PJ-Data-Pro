package com.pointless.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.pointless.quiz.*;

/**
 * 
 * This class is to minimize the amount of work to implement the quiz.
 * 
 * 
 * @author Won Lee
 * 
 * 0800 07/03, Won Lee:	2 hours to get elements from xml file.
 * 						=> Not capable of taking image right now.
 * 
 *
 */
public class QuestionLoader {

	public static List<Quiz> load(File file){
		List<Quiz> quizes = new ArrayList<Quiz>();
		
		if(file.isDirectory()){
	    	File[] files = file.listFiles(new MyFilenameFilter(true,null,null));
	    	for(File fl: files){
	    		System.out.println("File: "+fl.getName());
	    		File[] fls = fl.listFiles();
	    		//File[] fls = fl.listFiles(new MyFilenameFilter(true, "quiz", ".xml"));
	    		for(File f: fls){
	    			try {
	    				System.out.println(f.getName());
	    				if(f.getName().equals("quiz.xml")){
	    					quizes.addAll(readXML(f));
	    				}
					} catch (JDOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    	}
		}
		Collections.sort(quizes, new QuizComparator());
		return quizes;
	}
	
	/**
	 * Deal with one XML file to generate quizes from them
	 * 
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	private static List<Quiz> readXML(File file) throws JDOMException, IOException{
		List<Quiz> quizes = new ArrayList<Quiz>();
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = builder.build(file);
		List<Element> children = doc.getRootElement().getChildren();
		for(Element element: children){
			try{
				quizes.add(quizElementTraverser(element));
				//quizes.add(quizElementTraverser(element));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		System.out.println(quizes.getClass().toString());
		return quizes;
	}
	
	/**
	 * Deal with one <Quiz> Element.
	 * 
	 * @param element
	 * @throws Exception 
	 */
	private static Quiz quizElementTraverser(Element element) throws Exception{
		QuizType type = QuizType.valueOf(element.getAttributeValue("QuizType"));
		if(!element.getName().equals("Quiz")){
			throw new Exception("This is not Quiz Element");
		}
		String title = element.getChildTextTrim("Title");
		String desc = element.getChildTextTrim("Description");
		String category = element.getChildTextTrim("Category");
		List<Answer> ansList = answerElementTraverser(element.getChild("Answers").getChildren());
		
		System.out.println(title+desc+ansList.size());
		
		Quiz quiz = null;
		switch(type){
		case OutOfSeven: 
			quiz = new OutOfSeven(title, desc, category, ansList);
			System.out.println("outOfSeven Created");
			break;
			/*
		case TypeIt: 
			quiz = null;
			break;
		case Initials:
			;
			break;
		case FillInTheBlank:
			;
			break;
			*/
		case Other:
			quiz = null;
			break;
		default:
			throw new Exception("Invalid Quiztype");
		}
		return quiz;
	}
	
	/**
	 * 
	 * @param elements
	 * @return
	 */
	private static List<Answer> answerElementTraverser(List<Element> elements){
		List<Answer> list = new ArrayList<Answer>();
		for(Element element: elements){
			String phrase = element.getChildTextTrim("Phrase");
			String correct = element.getChildTextTrim("Correct");
			int score = Integer.valueOf(element.getChildText("point").trim());
			//list.add(new Answer(phrase, correct, score));
			list.add(new Answer(phrase, score));
			System.out.println(phrase+score);
		}
		Collections.shuffle(list);
		return list;
	}
	
	private static void getImage(File file){
		
	}
}
