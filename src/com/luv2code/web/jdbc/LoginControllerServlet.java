package com.luv2code.web.jdbc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
			case "REGISTER":
				register(request, response);
			case "CREATEUSER":
				validateUser(request, response);
			default:
				doPost(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}	}

	private void validateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		   String firstName = request.getParameter("firstName");
		   String lastName = request.getParameter("lastName");
		   String strUserName = request.getParameter("userName");
		   String strPassword = request.getParameter("password");	
		   HttpSession session = request.getSession();
		   int invalid = 0;
		   String strErrMsg = "User name or Password is invalid.";
		   if (strPassword.length() < 8 ) {
			   invalid = 1;
			   session.setAttribute("INVALID", invalid);
			   session.setAttribute("errorMsg", strErrMsg);
			   RequestDispatcher rd = request.getRequestDispatcher("/new_user.jsp");
				rd.forward(request, response);		   }
		   else {
			   loginDBUtil.enterNewUser(firstName, lastName, strUserName, strPassword);
			   RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
				rd.forward(request, response);		   }
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher rd = request.getRequestDispatcher("/new_user.jsp");
		rd.forward(request, response);		
	}

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
