package com.qa.callum.one.Presidents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class PresidentRecordKeeper {

	
	private static Logger logger = Logger.getLogger(PresidentRecordKeeper.class.getName());
	
	List<President> presidents = new ArrayList<>();
	List<Record> records = new ArrayList<>();

	public PresidentRecordKeeper() {
		
		FileHandler fh;
		
		try {
			fh = new FileHandler("President Log.txt");
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
			logger.setLevel(Level.FINE);
		} catch(Exception e) {
			logger.severe("Error!! " + e.toString());
		}
		
	}
	
	public void loadPres(String fileName) {
		try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr);) {

			do {
				String fileContent = br.readLine();
				if (fileContent == null) {
					break;
				}
				String[] content = null;
				content = fileContent.split(",");

				String name = content[0];
				String birthDate = content[1];
				String birthPlace = content[2];
				String deathDate = "\tDec 31 2069";
				String deathPlace = "\tDec 31 2069";
				if (!content[3].equals("\t")) {
					deathDate = content[3];
					deathPlace = content[4];
				}
				if (!name.equals("PRESIDENT")) {
					President pres = new President(name, birthDate, birthPlace, deathDate, deathPlace);
					presidents.add(pres);
				}
			} while (true);

		} catch (IOException e) {
			logger.severe("Error!! " + e.toString());
		}
	}

	public void presAlive() {
		Calendar myCalendar = Calendar.getInstance();
		Calendar compareCalendar = Calendar.getInstance();

		ArrayList<Date> importantDates = new ArrayList<>();
		for (President p : presidents) {
			importantDates.add(p.getBirthDate());
		}
		for (President p : presidents) {
			importantDates.add(p.getDeathDate());
		}

		int presCounter = 1;

		int mostPresses = 0;

		for (int i = 0; i < importantDates.size(); i++) {
			myCalendar.setTime(importantDates.get(i));
			myCalendar.add(Calendar.SECOND, 1);
			presCounter = 0;
			for (President p : presidents) {
				compareCalendar.setTime(p.getBirthDate());
				if (p.getBirthDate().before(myCalendar.getTime()) && p.getDeathDate().after(myCalendar.getTime())) {
					presCounter++;
				}
			}

			if (presCounter > mostPresses) {
				mostPresses = presCounter;
			}

			Record newRecord = new Record(myCalendar.getTime(), presCounter);
			records.add(newRecord);

		}

		int maxPres = records.stream().mapToInt(Record::getPres).max().orElse(0);

		List<Record> reducedList = records.stream().filter(currRec -> currRec.getPres() == maxPres)
				.collect(Collectors.toList());

		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

		reducedList.stream().forEach(currRec -> logger.info(
				"Year: " + yearFormat.format(currRec.getDate()) + " with " + currRec.getPres() + " presidents alive."));

	}

	public void printPresidents() {
		presidents.stream().forEach(president -> logger.info(president.toString()));
	}
}
