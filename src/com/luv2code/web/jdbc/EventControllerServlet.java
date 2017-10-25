package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

/**
 * Servlet implementation class EventControllerServlet
 */
@WebServlet("/EventControllerServlet")
public class EventControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EventDBUtil eventDBUtil;
	private ActivityDBUtil activityDBUtil;

	@Resource(name = "jdbc/screentime_project")
	private DataSource dataSource;

	@Override
	//very important to initialize all database util classes you need to use
	public void init() throws ServletException {
		super.init();
		eventDBUtil = new EventDBUtil(dataSource);
		activityDBUtil = new ActivityDBUtil(dataSource);
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
				listEvents(request, response);
				break;
			case "LOAD":
				loadEvent(request, response);
				break;
			case "UPDATE":
				updateEvent(request, response);
				break;
			case "CREATE":
				createEvent(request, response);
				break;
			case "DELETE":
				deleteEvent(request, response);
				break;
			case "SEARCH":
				searchEvent(request, response);
				break;
			default:
				listEvents(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	private void createEvent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		List<ScreenActivity> activities = activityDBUtil.getActivities();
		request.setAttribute("ACTIVITY_LIST", activities);

		RequestDispatcher rd = request.getRequestDispatcher("/add_event_form.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// route to the appropriate method
			switch (theCommand) {

			case "ADD":
				addEvent(request, response);
				break;

			default:
				listEvents(request, response);
			}

		} catch (Exception exc) {
			throw new ServletException(exc);
		}

	}

	private void searchEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read search name from form data
		String theSearchEvent = request.getParameter("theSearchEvent");

		// search activities from db util
		List<ScreenEvent> events = eventDBUtil.searchEvents(theSearchEvent);

		// add activities to the request
		request.setAttribute("EVENT_LIST", events);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_events.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int eventID = Integer.parseInt(request.getParameter("eventID"));
		// add the event to the database
		eventDBUtil.deleteEvent(eventID);
		// send back to main page (list page)
		listEvents(request, response);
	}

	private void updateEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read event info from form data
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String firstName = request.getParameter("firstName");
		String startDateTime = request.getParameter("startDateTime");
		String endDateTime = request.getParameter("endDateTime");
		String notes = request.getParameter("notes");
		int actID = Integer.parseInt(request.getParameter("actIDString"));
		String elapsedTime = findElapsedTime(startDateTime, endDateTime);
		// create a new event object
		ScreenEvent theEvent = new ScreenEvent(firstName, startDateTime, endDateTime, notes, eventID, actID,
				elapsedTime);
		// perform update on database
		eventDBUtil.updateEvent(theEvent);
		// send them back to the list_activities.jsp page
		listEvents(request, response);
	}
	private void loadEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read event id from form data
		String theEventID = request.getParameter("eventID");
		// get event from database
		ScreenEvent theEvent = EventDBUtil.getEvent(theEventID);
		List<ScreenActivity> activities = activityDBUtil.getActivities();
		// place activity in request attribute
		request.setAttribute("THE_EVENT", theEvent);
		request.setAttribute("ACTIVITY_LIST", activities);
		// send to jsp page: update_event_form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update_event_form.jsp");
		dispatcher.forward(request, response);
	}

	//add_event form calls this method after the add form is submitted
	private void addEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String actIDString = request.getParameter("actIDString");
		String firstName = request.getParameter("firstName");
		String startDateTime = request.getParameter("startDateTime");
		String endDateTime = request.getParameter("endDateTime");
		String notes = request.getParameter("notes");
		int actID = Integer.parseInt(actIDString);
		String elapsedTime = findElapsedTime(startDateTime, endDateTime);
		// create a new activity object
		ScreenEvent theEvent = new ScreenEvent(firstName, startDateTime, endDateTime, notes, actID, elapsedTime);
		// add the event to the database
		eventDBUtil.addEvent(theEvent);
		// send back to main page (list page)
		response.sendRedirect(request.getContextPath() + "/EventControllerServlet?command=LIST");
	}

	private void listEvents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = 1;
        int recordsPerPage = 10;
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
		// get events from db util
		List<ScreenEvent> events = eventDBUtil.getPaginationEvents((page - 1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = eventDBUtil.getNoOfRecords();
        System.out.println("noOfRecords: " + noOfRecords);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        System.out.println("noOfPages: " + noOfPages);
		// add events to the request
		request.setAttribute("EVENT_LIST", events);
		request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
		// send to jsp page (view)
		RequestDispatcher dispatcher2 = request.getRequestDispatcher("/list_events.jsp");
		dispatcher2.forward(request, response);
	}

	public String findElapsedTime(String startDateTime, String endDateTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date d1 = null;
		Date d2 = null;
		DateTime dt1 = new DateTime(d1);
		DateTime dt2 = new DateTime(d2);
		try {

			d1 = format.parse(startDateTime);
			d2 = format.parse(endDateTime);
			dt1 = new DateTime(d1);
			dt2 = new DateTime(d2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Days.daysBetween(dt1, dt2).getDays() + " days/" + Hours.hoursBetween(dt1, dt2).getHours() % 24
				+ " hours/" + Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes";
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
}
