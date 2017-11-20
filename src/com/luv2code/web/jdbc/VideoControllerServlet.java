package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class VideoControllerServlet
 */
@WebServlet("/VideoControllerServlet")
public class VideoControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private VideoDBUtil videoDBUtil;

	@Resource(name = "jdbc/screentime_project")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		videoDBUtil = new VideoDBUtil(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listVideos(request, response);
			default:
				listVideos(request, response);
			}

		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			listVideos(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void listVideos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get videos from db util
		List<Video> videos = videoDBUtil.getVideos();
		//GET RANDOM VIDEO FROM LIST OF VIDEOS, give it variable name randomVideo
		// add video to the request
		Random random = new Random();
		int index = random.nextInt(videos.size());
		String randomVideo = videos.get(index).getVideoURL().replace("watch?v=", "embed/");
		System.out.println(randomVideo);
		request.setAttribute("RANDOM_VIDEO", randomVideo);
		// send to jsp page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/MainMenu.jsp");
		dispatcher.forward(request, response);
	}

}
