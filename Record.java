package com.qa.callum.one.Presidents;

import java.util.Date;

public class Record {

	private Date date;
	private int pres;
	
	public Record(Date date, int pres) {
		this.date = date;
		this.pres = pres;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPres() {
		return pres;
	}

	public void setPres(int pres) {
		this.pres = pres;
	}
	
}
