<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
 "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Screentime Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/eventstyle.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Screentime Events</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">

			<!-- Put in new Add Event button. URL that leads back to the EventControllerServlet and contains the command to access the CREATE method which sends user
		to add_event_form.jsp is here-->
			<c:url var="createURL" value="EventControllerServlet">
				<c:param name="command" value="CREATE" />
			</c:url>
			<a href="${createURL}"> <input type="button" value="Add Event"
				class="add-event-button" />
			</a>


			<!--  add a search box -->
			<form action="EventControllerServlet" method="GET">

				<input type="hidden" name="command" value="SEARCH" />
				<p id="search-text">Search events by child name:</p>
				<input type="text" name="theSearchEvent" id="search-box" /> <input
					type="submit" value="Search" class="add-event-button"
					id="search-button" />

			</form>
			<form action="EventControllerServlet" method="GET">
				<input type="hidden" name="command" value="LIST" /> <input
					type="submit" value="Reset" class="add-event-button"
					id="reset-button" />
			</form>

			<table>
				<tr>
					<th>Child's Name</th>
					<th>Start Date/Time</th>
					<th>End Date/Time</th>
					<th>Notes</th>
					<th>Elapsed Time</th>
					<th>Activity ID</th>
					<th>Actions</th>
					<th></th>
				</tr>
				<c:forEach var="tempEvent" items="${EVENT_LIST}">
					<!-- set up a link for each event (use name of object properties, not database headings -->
					<c:url var="templink" value="EventControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="eventID" value="${tempEvent.eventID}" />
					</c:url>
					<c:url var="templink2" value="EventControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="eventID" value="${tempEvent.eventID}" />
					</c:url>
					<tr>
						<td>${tempEvent.firstName}</td>
						<td>${tempEvent.startDateTime}</td>
						<td>${tempEvent.endDateTime}</td>
						<td>${tempEvent.notes}</td>
						<td>${tempEvent.elapsedTime}</td>
						<td>${tempEvent.actID}</td>
						<!-- only the variables after the href will be usable after the delete/update -->
						<td><a href="${templink}">Update</a></td>
						<td><a href="${templink2}"
							onclick="if (!(confirm('Are you sure you want to delete the event?'))) 
						return false">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<br />
			<%--For displaying Previous link except for the 1st page --%>

			<br /> <br />
			<%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>

			<nav aria-label="Page navigation">
			<ul class="pagination">
				<li><c:if test="${currentPage != 1}">
						<a href="event.do?page=${currentPage - 1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</c:if></li>
				<c:forEach begin="1" end="${noOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							 <li><a href="#">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="event.do?page=${i}">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<li><c:if test="${currentPage lt noOfPages}">
						<a href="event.do?page=${currentPage + 1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</c:if></li>
			</ul>
			</nav>
		</div>
	</div>
</body>
<br />
<!--  ActivityControllerServlet-->
<div class="navbarSection">
<nav class="navbar fixed-bottom navbar-dark">
  <a class="navbar-brand" href="MainMenu.jsp">Main Menu</a>
</nav>
</div>
</html>