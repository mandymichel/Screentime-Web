package com.luv2code.web.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ActivityDBUtil {
	private static DataSource dataSource;
	
	public ActivityDBUtil (DataSource theDataSource) {
		dataSource = theDataSource;
	}
	public List<ScreenActivity> getActivities() {
		List<ScreenActivity> activities = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
		//get a connection
			myConn = dataSource.getConnection();
		//create sql statement
			String sql = "select * from activity order by name";
			myStmt = myConn.createStatement();
		//execute query
			myRs = myStmt.executeQuery(sql);
		//process result set
			while (myRs.next()) {
			//retrieve data from result set row
				int activityID = myRs.getInt("id");
				String activityType = myRs.getString("type");
				String activityName = myRs.getString("name");
				String activityEd = myRs.getString("educational");
			//create new activity object
				ScreenActivity tempActivity = new ScreenActivity(activityType, activityName, activityEd, activityID);
			//add it to the list
				activities.add(tempActivity);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//close JDBC objects
			close(myConn, myStmt, myRs);
		}
		return activities;		
	}
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addActivity(ScreenActivity theActivity) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		//create SQL statement for insert
		try {
			myConn = dataSource.getConnection();
			String sql = "insert into activity "
						+ "(type, name, educational) "
						+ "values (?, ?, ?)";
			myStmt = myConn.prepareStatement(sql);
			//set param values for the activity
			myStmt.setString(1, theActivity.getActivityType());
			myStmt.setString(2, theActivity.getActivityName());
			myStmt.setString(3,  theActivity.getActivityEd());
			//execute SQL insert
			myStmt.execute();//something wrong here...
		}
		finally {
			close(myConn, myStmt, null);
		}
		//clean up JDBC objects
	}
	public static ScreenActivity getActivity(String theActivityID) throws Exception {
		ScreenActivity theActivity = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int activityID;
		
		try {
			//convert student ID to int
			activityID = Integer.parseInt(theActivityID);
			//get connection to database
			myConn = dataSource.getConnection();
			//create sql to get selected activity
			String sql = "select * from activity where id=?";
			//create prepared statement
			myStmt = myConn.prepareStatement(sql);
			//set params
			myStmt.setInt(1, activityID);
			//execute statement
			myRs = myStmt.executeQuery();
			//retrieve result set from the result set row
			if (myRs.next()) {
				String activityType = myRs.getString("type");
				String activityName = myRs.getString("name");
				String activityEd = myRs.getString("educational");
				
				//use the activityID during construction
				theActivity = new ScreenActivity(activityType, activityName, activityEd, activityID);
			}
			else {
				throw new Exception("Could not find activity ID: " + activityID);
			}
		return theActivity;
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}
	public void updateActivity(ScreenActivity theActivity) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
		//get db connection
		myConn = dataSource.getConnection();
		//create SQL update statement
		String sql = "update activity "
					+ "set type=?, name=?, educational=? "
					+ "where id=?";
		//prepare statement
		myStmt = myConn.prepareStatement(sql);
		//set params
		myStmt.setString(1, theActivity.getActivityType());
		myStmt.setString(2, theActivity.getActivityName());
		myStmt.setString(3, theActivity.getActivityEd());
		myStmt.setInt(4, theActivity.getActivityID());
		//execute SQL statement
		myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}
	}
	public void deleteActivity(ScreenActivity theActivity) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
		//get db connection
		myConn = dataSource.getConnection();
		//create SQL update statement
		String sql = "DELETE FROM activity WHERE id = ?";
		//prepare statement
		myStmt = myConn.prepareStatement(sql);
		//set params
		myStmt.setInt(1, theActivity.getActivityID());
		//execute SQL statement
		myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}
	}
	public List<ScreenActivity> searchActivities(String theSearchActivity) throws Exception{
		  List<ScreenActivity> activities = new ArrayList<>();
          
          Connection myConn = null;
          PreparedStatement myStmt = null;
          ResultSet myRs = null;
          int activityID;
          
          try {
              
              // get connection to database
              myConn = dataSource.getConnection();
              
              //
              // only search by activity if theSearchActivity is not empty
              //
              if (theSearchActivity != null && theSearchActivity.trim().length() > 0) {
                  // create sql to search for students by name
                  String sql = "select * from activity where name like ?";
                  // create prepared statement
                  myStmt = myConn.prepareStatement(sql);
                  // set params
                  String theSearchActivityLike = "%" + theSearchActivity.toLowerCase() + "%";
                  myStmt.setString(1, theSearchActivityLike);                  
              } else {
                  // create sql to get all activities
                  String sql = "select * from activity order by name";
                  // create prepared statement
                  myStmt = myConn.prepareStatement(sql);
              }
              
              // execute statement
              myRs = myStmt.executeQuery();
              
              // retrieve data from result set row
              while (myRs.next()) {
                  
                  // retrieve data from result set row
                  activityID = myRs.getInt("id");
                  String activityType = myRs.getString("type");
                  String activityName = myRs.getString("name");
                  String activityEd = myRs.getString("educational");
                  
                  // create new student object
                  ScreenActivity tempActivity = new ScreenActivity(activityType, activityName, activityEd, activityID);
                  
                  // add it to the list of students
                  activities.add(tempActivity);            
              }
              
              return activities;
          }
          finally {
              // clean up JDBC objects
              close(myConn, myStmt, myRs);
          }
	}
}
