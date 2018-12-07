package com.qa.callum.one.Presidents;

public class Main {
	
	public static void main(String[] args) {

		String fileName = "C:\\presidents\\presidents.txt";
		
		PresidentRecordKeeper prk = new PresidentRecordKeeper();
		
		prk.loadPres(fileName);
		
		prk.presAlive();
		
	}

}
