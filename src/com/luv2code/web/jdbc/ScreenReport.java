package com.luv2code.web.jdbc;

public class ScreenReport {
	private String firstName = null;
	private String startDateTime = null;
	private String endDateTime = null;
	private String notes = null;
	private String educational = null;
	private int eventID = 0;
	private int actID = 0;

	public ScreenReport() {

	}

	public ScreenReport(String firstName, String startDateTime, String endDateTime, String notes, String educational, int eventID, int actID) {
		this.firstName = firstName;
		this.notes = notes;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.eventID = eventID;
		this.actID = actID;
		this.educational = educational;
	}
	public ScreenReport(String firstName, String startDateTime, String endDateTime, String notes, String educational, int eventID) {
		this.firstName = firstName;
		this.notes = notes;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.eventID = eventID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getActID() {
		return actID;
	}

	public void setActID(int actID) {
		this.actID = actID;
	}
	public String getEducational() {
		return educational;
	}
	public void setEducational(String educational) {
		this.educational = educational;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%d,%d", firstName, startDateTime, endDateTime, notes, educational, eventID, actID);
	}

	public String toFormattedString() {
		return String.format(
				"Name: %s, Start date: %s, End Date: %s, Notes: %s, Educational?: %s, Event: %d, Activity: %d",
				firstName, startDateTime, endDateTime, notes, educational, eventID, actID);
	}

}
