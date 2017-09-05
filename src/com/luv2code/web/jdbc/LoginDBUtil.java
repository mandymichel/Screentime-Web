package com.luv2code.web.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class LoginDBUtil {
	private static final String DBNAME = "tf_loginappdb";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "admin*";
	 
	private static final String LOGIN_QUERY = "select * from users where user_name=? and password=?";
	
	private static DataSource dataSource;
	
	public LoginDBUtil (DataSource theDataSource) {
		dataSource = theDataSource;
	}
	private static Connection getConnection() throws Exception {
		   Connection myConn = null;
		   try {
				myConn = dataSource.getConnection();
		   } catch (SQLException sqle) {
		      System.out.println("SQLException: Unable to open connection to db: "+sqle.getMessage());
		      throw sqle;
		   } catch(Exception e) {
		      System.out.println("Exception: Unable to open connection to db: "+e.getMessage());
		      throw e;
		   }
		   return myConn;
	}
	public boolean authenticateLogin(String strUserName, String strPassword) throws Exception {
		   boolean isValid = false;
		   Connection conn = null;
		   try {
		     conn = getConnection();
		     PreparedStatement prepStmt = conn.prepareStatement(LOGIN_QUERY);
		     prepStmt.setString(1, strUserName);
		     prepStmt.setString(2, strPassword);
		     ResultSet rs = prepStmt.executeQuery();
		     if(rs.next()) {
		       System.out.println("User login is valid in DB");
		       isValid = true; 
		     }
		  } catch(Exception e) {
		    System.out.println("validateLogon: Error while validating password: "+e.getMessage());
		    throw e;
		  } 
		  return isValid;
		}
}