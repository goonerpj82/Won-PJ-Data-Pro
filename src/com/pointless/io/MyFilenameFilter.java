package com.pointless.io;

import java.io.File;
import java.io.FilenameFilter;

public class MyFilenameFilter implements FilenameFilter {
	private boolean acceptD;
	private String extension;
	private String name;

	public MyFilenameFilter(boolean acceptD, String name, String extension) {
		this.acceptD = acceptD;
		this.extension = extension;
		this.name = name;
	}

	@Override
	public boolean accept(File arg0, String arg1) {
		// TODO Auto-generated method stub
		File file = new File(arg0.toString()+File.separator	+arg1);
		System.out.println(file.toString());
		if(file.isDirectory() && !acceptD){
			return false;
		}
		if(extension != null && !file.getName().endsWith(extension)){
			return false;
		}
		if(name != null && !file.getName().equals(name)){
			return false;
		}
		return true;
	}

}
