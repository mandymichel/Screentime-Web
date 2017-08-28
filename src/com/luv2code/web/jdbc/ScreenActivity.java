package com.luv2code.web.jdbc;
public class ScreenActivity {

	private String activityType = null;
	private String activityName = null;
	private String activityEd = null;
	private int activityID;

	public ScreenActivity(String activityType, String activityName, String activityEd, int activityID) {
		this.activityType = activityType;
		this.activityName = activityName;
		this.activityEd = activityEd;
		this.activityID = activityID;
	}
	public ScreenActivity(String activityType, String activityName, String activityEd) {
		this.activityType = activityType;
		this.activityName = activityName;
		this.activityEd = activityEd;
	}
	public String getActivityEd() {
		return activityEd;
	}

	public void setActivityEd(String activityEd) {
		this.activityEd = activityEd;
	}

	public int getActivityID() {
		return activityID;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Override
	public String toString() {
		return activityID + " " + activityType + "," + activityName + "," + activityEd;
	}
}