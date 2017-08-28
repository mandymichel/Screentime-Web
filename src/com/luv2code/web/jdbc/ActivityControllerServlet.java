package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ActivityControllerServlet
 */
@WebServlet("/ActivityControllerServlet")
public class ActivityControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ActivityDBUtil activityDBUtil;
	private EventDBUtil eventDBUtil;

	@Resource(name = "jdbc/screentime_project")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			activityDBUtil = new ActivityDBUtil(dataSource);
			eventDBUtil = new EventDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listActivities(request, response);
				break;
			case "LOAD":
				loadActivity(request, response);
				break;
			case "UPDATE":
				updateActivity(request, response);
				break;
			case "DELETE":
				deleteActivity(request, response);
				break;
			case "SEARCH":
				searchActivities(request, response);
				break;
			default:
				listActivities(request, response);
			}

		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// route to the appropriate method
			switch (theCommand) {

			case "ADD":
				addActivities(request, response);
				break;

			default:
				listActivities(request, response);
			}

		} catch (Exception exc) {
			throw new ServletException(exc);
		}

	}

	private void searchActivities(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read search name from form data
		String theSearchActivity = request.getParameter("theSearchActivity");

		// search activities from db util
		List<ScreenActivity> activities = activityDBUtil.searchActivities(theSearchActivity);

		// add activities to the request
		request.setAttribute("ACTIVITY_LIST", activities);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_activities.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read activity info from form data
		int activityID = Integer.parseInt(request.getParameter("activityID"));
		String activityType = request.getParameter("activityType");
		String activityName = request.getParameter("activityName");
		String activityEd = request.getParameter("activityEd");
		// create a new activity object
		ScreenActivity theActivity = new ScreenActivity(activityType, activityName, activityEd, activityID);
		List<ScreenEvent> events = new ArrayList<>();
		events = eventDBUtil.getEvents();
		for (ScreenEvent s: events) {
			if (activityID == s.getActID()) {
				request.setAttribute("THE_ACTIVITY_ID", activityID);
				// send to jsp page: update_activity_form.jsp
				RequestDispatcher dispatcher = request.getRequestDispatcher("/delete_denied_form.jsp");
				dispatcher.forward(request, response);
			}
		}
			// perform update on database
			activityDBUtil.deleteActivity(theActivity);
			// send them back to the list_activities.jsp page
			listActivities(request, response);
		
	}

	private void updateActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read activity info from form data
		int activityID = Integer.parseInt(request.getParameter("activityID"));
		String activityType = request.getParameter("activityType");
		String activityName = request.getParameter("activityName");
		String activityEd = request.getParameter("activityEd");
		// create a new activity object
		ScreenActivity theActivity = new ScreenActivity(activityType, activityName, activityEd, activityID);
		// perform update on database
		activityDBUtil.updateActivity(theActivity);
		// send them back to the list_activities.jsp page
		listActivities(request, response);
	}

	private void loadActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read activity id from form data
		String theActivityID = request.getParameter("activityID");
		// get activity from database
		ScreenActivity theActivity = ActivityDBUtil.getActivity(theActivityID);
		// place activity in request attribute
		request.setAttribute("THE_ACTIVITY", theActivity);
		// send to jsp page: update_activity_form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update_activity_form.jsp");
		dispatcher.forward(request, response);
	}

	private void addActivities(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read activity from form data
		String activityType = request.getParameter("activityType");
		String activityName = request.getParameter("activityName");
		String activityEd = request.getParameter("activityEd");
		// create a new activity object
		ScreenActivity theActivity = new ScreenActivity(activityType, activityName, activityEd);
		// add the activity to the database
		activityDBUtil.addActivity(theActivity);
		response.sendRedirect(request.getContextPath() + "/ActivityControllerServlet?command=LIST");
	}

	private void listActivities(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students from db util
		List<ScreenActivity> activities = activityDBUtil.getActivities();
		// add students to the request
		request.setAttribute("ACTIVITY_LIST", activities);
		// send to jsp page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_activities.jsp");
		dispatcher.forward(request, response);
	}

}
