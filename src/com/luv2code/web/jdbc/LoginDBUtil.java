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
	private static final String salt = BCrypt.gensalt();
	 	
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
	public boolean authenticateLogin(String userName, String userPassword) throws SQLException {
		System.out.println("Made it to the authenticateLogin method");
		System.out.println(salt);
		// create JDBC objects
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
			// get connection
			conn = dataSource.getConnection();
			
			// create SQL String and PreparedStatement to SELECT userEmail, if name matches
			String sql = "SELECT * FROM users WHERE user_name LIKE ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userName);

			// execute SQL statement
			rs = stmt.executeQuery();
			// return boolean
			if (rs.next()) {
				
				String hashed = rs.getString("password");
				System.out.println(hashed);
				// verify password
				if (BCrypt.checkpw(userPassword, hashed)) {
					System.out.println("password correct");
					return true;
				}
				else {
					System.out.println("password incorrect");
					return false;
				}
			} else {
				// no user
				return false;
			}
	}
	//fix this for encrypting password and THEN entering it into the database
	public void enterNewUser(String firstName, String lastName, String strUserName, String strPassword) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		String hashedPassword = BCrypt.hashpw(strPassword, salt);//fix this after reviewing BCrypt
		// create SQL statement for insert
		try {
			myConn = dataSource.getConnection();
			String sql = "insert into users "
					+ "(first_name, last_name, user_name, password)"
					+ "values (?, ?, ?, ?)";
			myStmt = myConn.prepareStatement(sql);
			// set param values for the activity
			myStmt.setString(1, firstName);
			myStmt.setString(2, lastName);
			myStmt.setString(3, strUserName);
			myStmt.setString(4, hashedPassword);
			// execute SQL insert
			myStmt.execute();
			System.out.println("Registered a new user.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// clean up JDBC objects		
	}
}