package com.luv2code.web.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class EventDBUtil {
	private static DataSource dataSource;
    private int noOfRecords;

	public EventDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<ScreenEvent> getPaginationEvents(int offset,
            int noOfRecords) throws Exception {
		List<ScreenEvent> events = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
		//get a connection
			myConn = dataSource.getConnection();
		//create sql statement
			String sql = "select SQL_CALC_FOUND_ROWS * from event order by first_name limit " + offset + ", " + noOfRecords;
			myStmt = myConn.createStatement();
		//execute query
			myRs = myStmt.executeQuery(sql);
		//process result set
			while (myRs.next()) {
			//retrieve data from result set row
				String firstName = myRs.getString("first_name");
				String startDateTime = myRs.getString("start_date_time");
				String endDateTime = myRs.getString("end_date_time");
				String notes = myRs.getString("notes");
				int eventID = myRs.getInt("id");
				int actID = myRs.getInt("act_id");
				String elapsedTime = myRs.getString("elapsed_time");
			//create new activity object
				ScreenEvent tempEvent = new ScreenEvent(firstName, startDateTime, endDateTime, notes, eventID, actID, elapsedTime);
			//add it to the list
				events.add(tempEvent);
			}
			myRs.close();

            myRs = myStmt.executeQuery("SELECT FOUND_ROWS()");
            
        if(myRs.next()) {
             this.noOfRecords = myRs.getInt(1);
             System.out.println(this.noOfRecords);
        }
		}
        catch (SQLException e) {
            e.printStackTrace();
        }finally
        {
            try {
                if(myStmt != null)
                    myStmt.close();
                if(myConn != null)
                    myConn.close();
                } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		//for (ScreenEvent e: events) {
		//	System.out.println(e);
		//}
		System.out.println("noOfRecords in DAO: " + noOfRecords);
		return events;
	}
	 public int getNoOfRecords() {
		    return noOfRecords;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addEvent(ScreenEvent theEvent) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		// create SQL statement for insert
		try {
			myConn = dataSource.getConnection();
			String sql = "insert into event "
					+ "(first_name, start_date_time, end_date_time, notes, act_id, elapsed_time)"
					+ "values (?, ?, ?, ?, ?, ?)";
			myStmt = myConn.prepareStatement(sql);
			// set param values for the activity
			myStmt.setString(1, theEvent.getFirstName());
			myStmt.setString(2, theEvent.getStartDateTime());
			myStmt.setString(3, theEvent.getEndDateTime());
			myStmt.setString(4, theEvent.getNotes());
			myStmt.setInt(5, theEvent.getActID());
			myStmt.setString(6, theEvent.getElapsedTime());
			// execute SQL insert
			myStmt.execute();// something wrong here...
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, null);
		}
		// clean up JDBC objects
	}

	public void deleteEvent(int eventID) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			// get db connection
			myConn = dataSource.getConnection();
			// create SQL update statement
			String sql = "DELETE FROM event WHERE id = ?";
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			// set params
			myStmt.setInt(1, eventID);
			// execute SQL statement
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	public void updateEvent(ScreenEvent theEvent) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			// get db connection
			myConn = dataSource.getConnection();
			// create SQL update statement
			String sql = "update event "
					+ "set first_name=?, start_date_time=?, end_date_time=?, notes=?, act_id=?, elapsed_time=?"
					+ "where id=?";
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			// set params
			myStmt.setString(1, theEvent.getFirstName());
			myStmt.setString(2, theEvent.getStartDateTime());
			myStmt.setString(3, theEvent.getEndDateTime());
			myStmt.setString(4, theEvent.getNotes());
			myStmt.setInt(5, theEvent.getActID());
			myStmt.setString(6, theEvent.getElapsedTime());
			myStmt.setInt(7, theEvent.getEventID());
			// execute SQL statement
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	public static ScreenEvent getEvent(String theEventID) throws Exception {
		ScreenEvent theEvent = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int eventID;

		try {
			// convert student ID to int
			eventID = Integer.parseInt(theEventID);
			// get connection to database
			myConn = dataSource.getConnection();
			// create sql to get selected activity
			String sql = "select * from event where id=?";
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			// set params
			myStmt.setInt(1, eventID);
			// execute statement
			myRs = myStmt.executeQuery();
			// retrieve result set from the result set row
			if (myRs.next()) {
				String firstName = myRs.getString("first_name");
				String startDateTime = myRs.getString("start_date_time");
				String endDateTime = myRs.getString("end_date_time");
				String notes = myRs.getString("notes");
				eventID = myRs.getInt("id");
				int actID = myRs.getInt("act_id");
				String elapsedTime = myRs.getString("elapsed_time");

				// use the eventID during construction
				theEvent = new ScreenEvent(firstName, startDateTime, endDateTime, notes, eventID, actID, elapsedTime);
			} else {
				throw new Exception("Could not find event ID: " + eventID);
			}
			return theEvent;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	public List<ScreenEvent> getEvents() throws Exception {
		List<ScreenEvent> events = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
		//get a connection
			myConn = dataSource.getConnection();
		//create sql statement
			String sql = "select * from event order by first_name";
			myStmt = myConn.createStatement();
		//execute query
			myRs = myStmt.executeQuery(sql);
		//process result set
			while (myRs.next()) {
			//retrieve data from result set row
				String firstName = myRs.getString("first_name");
				String startDateTime = myRs.getString("start_date_time");
				String endDateTime = myRs.getString("end_date_time");
				String notes = myRs.getString("notes");
				int eventID = myRs.getInt("id");
				int actID = myRs.getInt("act_id");
				String elapsedTime = myRs.getString("elapsed_time");
			//create new activity object
				ScreenEvent tempEvent = new ScreenEvent(firstName, startDateTime, endDateTime, notes, eventID, actID, elapsedTime);
			//add it to the list
				events.add(tempEvent);
			}
			return events;
		}
		finally {
			//close JDBC objects
			close(myConn, myStmt, myRs);
		}
		
	}

	public List<ScreenEvent> searchEvents(String theSearchEvent) throws Exception {
		List<ScreenEvent> events = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int eventID;

		try {

			// get connection to database
			myConn = dataSource.getConnection();

			//
			// only search by name if theSearchEvent is not empty
			//
			if (theSearchEvent != null && theSearchEvent.trim().length() > 0) {
				// create sql to search for event by child first name
				String sql = "select * from event where first_name like ?";
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
				// set params
				String theSearchEventLike = "%" + theSearchEvent.toLowerCase() + "%";
				myStmt.setString(1, theSearchEventLike);
			} else {
				// create sql to get all students
				String sql = "select * from event order by start_date_time";
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
			}

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			while (myRs.next()) {

				// retrieve data from result set row
				eventID = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String startDateTime = myRs.getString("start_date_time");
				String endDateTime = myRs.getString("end_date_time");
				String notes = myRs.getString("notes");
				int actID = myRs.getInt("act_id");
				String elapsedTime = myRs.getString("elapsed_time");
				// create new student object
				ScreenEvent tempEvent = new ScreenEvent(firstName, startDateTime, endDateTime, notes, eventID, actID,
						elapsedTime);

				// add it to the list of students
				events.add(tempEvent);
			}

			return events;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public List<ScreenReport> searchByName(String firstName) throws Exception {
		List<ScreenReport> reports = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			// get connection to database
			myConn = dataSource.getConnection();

			//
			// only search by name if theSearchEvent is not empty
			//
			if (firstName != null && firstName.trim().length() > 0) {
				// create sql to search for event by child first name
				String sql = "\n"
						+ "select * from event inner join activity on event.act_id = activity.id where first_name like ?";
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
				// set params
				String firstNameLike = "%" + firstName.toLowerCase() + "%";
				myStmt.setString(1, firstNameLike);
			}

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			while (myRs.next()) {

				// fix this stuff below!!
				firstName = myRs.getString("first_name");
				String startDateTime = myRs.getString("start_date_time");
				String endDateTime = myRs.getString("end_date_time");
				String notes = myRs.getString("notes");
				String educational = myRs.getString("educational");
				int eventID = myRs.getInt("id");
				int actID = myRs.getInt("act_id");
				// create new student object
				ScreenReport tempReport = new ScreenReport(firstName, startDateTime, endDateTime, notes, educational,
						eventID, actID);
				// add it to the list of students
				reports.add(tempReport);
			}
			return reports;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
}