package com.gridnote.app.model;

public class Diary {
	private String date;
	private String week;
	private String weather;
	private String firstanswer;
	private String secondanswer;
	private String thirdanswer;
	private String forthanswer;
	private String fifthanswer;
	private String sixthanswer;
	private int id;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	//一到第六个问题
	public String getFirstAnswer() {
		return firstanswer;
	}
	public void setFirstAnswer(String firstanswer) {
		this.firstanswer = firstanswer;
	}
	
	public String getSecondAnswer() {
		return secondanswer;
	}
	public void setSecondAnswer(String secondanswer) {
		this.secondanswer = secondanswer;
	}
	
	public String getThirdAnswer() {
		return thirdanswer;
	}
	public void setThirdAnswer(String thirdanswer) {
		this.thirdanswer = thirdanswer;
	}
		
	
	public String getForthAnswer() {
		return forthanswer;
	}
	public void setForthAnswer(String forthanswer) {
		this.forthanswer = forthanswer;
	}
	
	public String getFifthAnswer() {
		return fifthanswer;
	}
	public void setFifthAnswer(String fifthanswer) {
		this.fifthanswer = fifthanswer;
	}
	
	public String getSixthAnswer() {
		return sixthanswer;
	}
	public void setSixthAnswer(String sixthanswer) {
		this.sixthanswer = sixthanswer;
	}
	
//////////////////////////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Diary [date=" + date + ", week=" + week + ", weather="
				+ weather + ", firstanswer=" + fifthanswer + ", secondanswer="
				+ secondanswer + ", thirdanswer=" + thirdanswer + ", forthanswer="
				+ forthanswer + ", fifthanswer=" + fifthanswer + ", sixthanswer="
				+ sixthanswer +	", id=" + id + "]";
	}
}
