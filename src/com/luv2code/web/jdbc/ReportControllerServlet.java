package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

/**
 * Servlet implementation class EventControllerServlet
 */
@WebServlet("/ReportControllerServlet")
public class ReportControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EventDBUtil eventDBUtil;
	private ActivityDBUtil activityDBUtil;

	@Resource(name = "jdbc/screentime_project")
	private DataSource dataSource;

	@Override
	// very important to initialize all database util classes you need to use
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

			if (theCommand == null) {
				theCommand = "CREATE";
			}
			// route to the appropriate method
			switch (theCommand) {

			case "CREATE":
				createReport(request, response);
				break;
			default:
				createReport(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	private void createReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ScreenEvent> events = eventDBUtil.getEvents();
		Set<String> children = new HashSet<String>();
		for (ScreenEvent s: events) {
			children.add(s.getFirstName());
		}
		request.setAttribute("CHILD_LIST", children);
		RequestDispatcher rd = request.getRequestDispatcher("/screen_report.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			if (theCommand == null) {
				theCommand = "DISPLAY";
			}
			// route to the appropriate method
			switch (theCommand) {

			case "DISPLAY":
				chooseReportType(request, response);
				break;
			default:
				chooseReportType(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}

	}

	private void chooseReportType(HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String reportType = request.getParameter("reportType");
		switch (reportType) {
		case "averageTimePerDay":
			try {
				avTimePerDay(firstName, request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "averageTimePerDayUneducational":
			try {
				avTimePerDayUneducational(firstName, request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "averageTimePerDayEducational":
			try {
				avTimePerDayEducational(firstName, request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "timeEducationalMonthlyForYear":
			try {
				timeEducationalMonthlyYear(firstName, request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "timeUneducationalMonthlyForYear":
			try {
				timeUneducationalMonthlyYear(firstName, request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "timeMonthlyForYear":
			try {
				timeMonthlyYear(firstName, request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	private void timeMonthlyYear(String firstName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ScreenReport> reports = eventDBUtil.searchByName(firstName);
		List<Integer> sumMinuteMonth = addMonthMinutes(reports);
		List<String> months = new ArrayList<>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		request.setAttribute("MONTHS", months);
		request.setAttribute("TIMEMONTHLYYEAR", sumMinuteMonth); // send to JSP page (view)
		request.setAttribute("CHILDNAME", firstName);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/time_monthly_year.jsp");
		dispatcher.forward(request, response);
	}

	private List<Integer> addMonthMinutes(List<ScreenReport> reports) {
		List<Integer> janMinutes = new ArrayList<Integer>();
		List<Integer> febMinutes = new ArrayList<Integer>();
		List<Integer> marMinutes = new ArrayList<Integer>();
		List<Integer> aprMinutes = new ArrayList<Integer>();
		List<Integer> mayMinutes = new ArrayList<Integer>();
		List<Integer> junMinutes = new ArrayList<Integer>();
		List<Integer> julMinutes = new ArrayList<Integer>();
		List<Integer> augMinutes = new ArrayList<Integer>();
		List<Integer> sepMinutes = new ArrayList<Integer>();
		List<Integer> octMinutes = new ArrayList<Integer>();
		List<Integer> novMinutes = new ArrayList<Integer>();
		List<Integer> decMinutes = new ArrayList<Integer>();
		for (ScreenReport r : reports) {
			//get the list from the map
			String newMonth = findMonth(r.getStartDateTime());
			int elapsedTimeEvent = findElapsedTime(r.getStartDateTime(), r.getEndDateTime());
			switch (newMonth) {
				case "January":
						janMinutes.add(elapsedTimeEvent);
						break;
				case "February": 
						febMinutes.add(elapsedTimeEvent);
						break;
				case "March":
						marMinutes.add(elapsedTimeEvent);
						break;
				case "April":
						aprMinutes.add(elapsedTimeEvent);
						break;
				case "May":
						mayMinutes.add(elapsedTimeEvent);
						break;
				case "June":
						junMinutes.add(elapsedTimeEvent);
						break;
				case "July":
						julMinutes.add(elapsedTimeEvent);
						break;
				case "August":
						augMinutes.add(elapsedTimeEvent);
						break;
				case "September":
						sepMinutes.add(elapsedTimeEvent);
						break;
				case "October":
						octMinutes.add(elapsedTimeEvent);
						break;
				case "November":
						novMinutes.add(elapsedTimeEvent);
						break;
				case "December":
						decMinutes.add(elapsedTimeEvent);
						break;
				default: 
						decMinutes.add(elapsedTimeEvent);
						break;
			}
		}	
		int janSum = janMinutes.stream().mapToInt(Integer::intValue).sum();
		int febSum = febMinutes.stream().mapToInt(Integer::intValue).sum();
		int marSum = marMinutes.stream().mapToInt(Integer::intValue).sum();
		int aprSum = aprMinutes.stream().mapToInt(Integer::intValue).sum();
		int maySum = mayMinutes.stream().mapToInt(Integer::intValue).sum();
		int junSum = junMinutes.stream().mapToInt(Integer::intValue).sum();
		int julSum = julMinutes.stream().mapToInt(Integer::intValue).sum();
		int augSum = augMinutes.stream().mapToInt(Integer::intValue).sum();
		int sepSum = sepMinutes.stream().mapToInt(Integer::intValue).sum();
		int octSum = octMinutes.stream().mapToInt(Integer::intValue).sum();
		int novSum = novMinutes.stream().mapToInt(Integer::intValue).sum();
		int decSum = decMinutes.stream().mapToInt(Integer::intValue).sum();
		List<Integer> sumMinuteMonth = new ArrayList<>();
		sumMinuteMonth.add(janSum);
		sumMinuteMonth.add(febSum);
		sumMinuteMonth.add(marSum);
		sumMinuteMonth.add(aprSum);
		sumMinuteMonth.add(maySum);
		sumMinuteMonth.add(junSum);
		sumMinuteMonth.add(julSum);
		sumMinuteMonth.add(augSum);
		sumMinuteMonth.add(sepSum);
		sumMinuteMonth.add(octSum);
		sumMinuteMonth.add(novSum);
		sumMinuteMonth.add(decSum);		
		return sumMinuteMonth;
	}

	private String findMonth(String startDateTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date d = null;
		DateTime dt = new DateTime(d);
		try {
			d = format.parse(startDateTime);
			dt = new DateTime(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int monthNumber = dt.getMonthOfYear();
		String monthString;
		switch (monthNumber) {
        case 1:  monthString = "January";
                 break;
        case 2:  monthString = "February";
                 break;
        case 3:  monthString = "March";
                 break;
        case 4:  monthString = "April";
                 break;
        case 5:  monthString = "May";
                 break;
        case 6:  monthString = "June";
                 break;
        case 7:  monthString = "July";
                 break;
        case 8:  monthString = "August";
                 break;
        case 9:  monthString = "September";
                 break;
        case 10: monthString = "October";
                 break;
        case 11: monthString = "November";
                 break;
        case 12: monthString = "December";
                 break;
        default: monthString = "Invalid month";
                 break;
		}
		return monthString;
	}

	private void timeUneducationalMonthlyYear(String firstName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<ScreenReport> tempReports = eventDBUtil.searchByName(firstName);
		List<ScreenReport> reports = new ArrayList<>();
		
		for (ScreenReport r: tempReports) {
			if (r.getEducational().equals("n")) {
				reports.add(r);
			}
		}	
		List<Integer> sumMinuteMonth = addMonthMinutes(reports);
		request.setAttribute("TIMEMONTHLYYEAR", sumMinuteMonth); // send to JSP page (view)
		request.setAttribute("CHILDNAME", firstName);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/uneducational_time_monthly_year.jsp");
		dispatcher.forward(request, response);
	}

	private void timeEducationalMonthlyYear(String firstName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<ScreenReport> tempReports = eventDBUtil.searchByName(firstName);
		List<ScreenReport> reports = new ArrayList<>();

		for (ScreenReport r: tempReports) {
			if (r.getEducational().equals("y")) {
				reports.add(r);
			}
		}
		List<Integer> sumMinuteMonth = addMonthMinutes(reports);
		
		request.setAttribute("TIMEMONTHLYYEAR", sumMinuteMonth); // send to JSP page (view)
		request.setAttribute("CHILDNAME", firstName);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/educational_time_monthly_year.jsp");
		dispatcher.forward(request, response);
	}

	private void avTimePerDayEducational(String firstName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ScreenReport> reports = eventDBUtil.searchByName(firstName);
		int avMinutes = 0;
		int numberDays = 0;
		int totalMinutes = 0;

		for (ScreenReport r : reports) {
			if (r.getEducational().equals("y")) {
				int minutes = findElapsedTime(r.getStartDateTime(), r.getEndDateTime());
				totalMinutes = totalMinutes + minutes;
			}
		}
		numberDays = findNumberDays(reports);

		if (numberDays != 0) {
			avMinutes = totalMinutes / numberDays;
		} else {
			avMinutes = 0;
		}
		Integer averageMinutes = new Integer(avMinutes);
		request.setAttribute("CHILDAVERAGE", averageMinutes); // send to JSP page (view)
		request.setAttribute("CHILDNAME", firstName);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/screen_report.jsp");
		dispatcher.forward(request, response);
	}

	private void avTimePerDayUneducational(String firstName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ScreenReport> reports = eventDBUtil.searchByName(firstName);
		int avMinutes = 0;
		int numberDays = 0;
		int totalMinutes = 0;

		for (ScreenReport r : reports) {
			if (r.getEducational().equals("n")) {
				int minutes = findElapsedTime(r.getStartDateTime(), r.getEndDateTime());
				totalMinutes = totalMinutes + minutes;
			}
		}
		numberDays = findNumberDays(reports);

		if (numberDays != 0) {
			avMinutes = totalMinutes / numberDays;
		} else {
			avMinutes = 0;
		}
		Integer averageMinutes = new Integer(avMinutes);
		request.setAttribute("CHILDAVERAGE", averageMinutes); // send to JSP page (view)
		request.setAttribute("CHILDNAME", firstName);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/screen_report.jsp");
		dispatcher.forward(request, response);
	}

	private int findNumberDays(List<ScreenReport> reports) {
		Set<Integer> set = new HashSet<Integer>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date d = null;
		DateTime dt = new DateTime(d);
		for (ScreenReport r : reports) {
			try {
				d = format.parse(r.getStartDateTime());
				dt = new DateTime(d);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int dayNumber = dt.getDayOfMonth();
			Integer intObj = new Integer(dayNumber);
			set.add(intObj);
		}
		int numberDays = set.size();
		return numberDays;
	}

	private void avTimePerDay(String firstName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ScreenEvent> chosenChildEvents = eventDBUtil.searchEvents(firstName);
		int avMinutes = 0;
		int numberOfDays = 0;
		int totalMinutes = 0;
		for (ScreenEvent s : chosenChildEvents) {
			int minutes = findElapsedTime(s.getStartDateTime(), s.getEndDateTime());
			totalMinutes = totalMinutes + minutes;
			numberOfDays++;
		}
		avMinutes = totalMinutes / numberOfDays;
		Integer averageMinutes = new Integer(avMinutes);
		request.setAttribute("CHILDAVERAGE", averageMinutes);
		request.setAttribute("CHILDNAME", firstName);
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/screen_report.jsp");
		dispatcher.forward(request, response);
	}

	public int findElapsedTime(String startDateTime, String endDateTime) {
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

		int totalMinutes = Minutes.minutesBetween(dt1, dt2).getMinutes();
		return totalMinutes;
	}

}