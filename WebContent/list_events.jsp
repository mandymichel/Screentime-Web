<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Screentime Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/eventstyle.css">
</head>

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
		<a href="${createURL}" >
			<input type="button" value="Add Event" class="add-event-button" />
		</a> 
		
		
		<!--  add a search box -->
		<form action="EventControllerServlet" method="GET">

			<input type="hidden" name="command" value="SEARCH" />

			Search events by child name: <input type="text" name="theSearchEvent" />

			<input type="submit" value="Search" class="add-event-button" />

		</form>
		<form action="EventControllerServlet" method="GET">
			<input type="hidden" name="command" value="LIST" />
			<input type="submit" value="Reset" class="add-event-button" />
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
			<th>       </th>
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
					<td> ${tempEvent.firstName} </td>
					<td> ${tempEvent.startDateTime} </td>
					<td> ${tempEvent.endDateTime} </td>
					<td> ${tempEvent.notes}	</td>
					<td> ${tempEvent.elapsedTime} </td>
					<td> ${tempEvent.actID} </td>
					<!-- only the variables after the href will be usable after the delete/update -->
					<td> <a href="${templink}">Update</a> </td>
					<td> <a href="${templink2}"
						onclick="if (!(confirm('Are you sure you want to delete the event?'))) 
						return false">Delete</a> </td>		
				</tr>	
		</c:forEach>
		</table>
		</div>
	</div>
	<a href="MainMenu.jsp">Back to Main Menu</a>
	<br>
	<a href="ActivityControllerServlet">Go to Activities</a>
	
</body>
</html>