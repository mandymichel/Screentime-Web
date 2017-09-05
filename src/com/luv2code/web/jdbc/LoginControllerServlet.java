package com.luv2code.web.jdbc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoginControllerServlet
 */
@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	private static final String HOME_PAGE = "MainMenu.jsp";
	private static final String LOGIN_PAGE = "Login.jsp";
	
	private static final long serialVersionUID = 1L;
	
	private LoginDBUtil loginDBUtil;

	@Resource(name = "jdbc/screentime_project")
	private DataSource dataSource;

	@Override
	//very important to initialize all database util classes you need to use
	public void init() throws ServletException {
		super.init();
		loginDBUtil = new LoginDBUtil(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LOGIN";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LOGIN":
				doPost(request, response);
				break;
			default:
				doPost(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String strUserName = request.getParameter("userName");
		   String strPassword = request.getParameter("password");
		   String strErrMsg = null;
		   HttpSession session = request.getSession();
		   boolean isValidLogon = false;
		   int invalid = 0;
		   try {
		     isValidLogon = loginDBUtil.authenticateLogin(strUserName, strPassword);
		     if(isValidLogon) {
		        session.setAttribute("userName", strUserName);
		     } else {
		        strErrMsg = "User name or Password is invalid.";
		     }
		   } catch(Exception e) {
		     strErrMsg = "Unable to validate user / password in database";
		   }
		 
		   if(isValidLogon) {
		     response.sendRedirect(HOME_PAGE);
		   } else {
			 invalid = 1;
			 session.setAttribute("INVALID", invalid);
		     session.setAttribute("errorMsg", strErrMsg);
		     response.sendRedirect(LOGIN_PAGE);
		   }
		 
		}
	

}
