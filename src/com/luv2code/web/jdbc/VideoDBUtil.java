package com.luv2code.web.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class VideoDBUtil {
	private static DataSource dataSource;
	
	public VideoDBUtil (DataSource theDataSource) {
		dataSource = theDataSource;
	}
	public List<Video> getVideos() {
		List<Video> videos = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
		//get a connection
			myConn = dataSource.getConnection();
		//create sql statement
			String sql = "select * from video";
			myStmt = myConn.createStatement();
		//execute query
			myRs = myStmt.executeQuery(sql);
		//process result set
			while (myRs.next()) {
			//retrieve data from result set row
				int id = myRs.getInt("id");
				String videoURL = myRs.getString("rand_video");
			//create new video object
				Video video = new Video(id, videoURL);
			//add it to the list
				videos.add(video);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//close JDBC objects
			close(myConn, myStmt, myRs);
		}
		return videos;		
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
}