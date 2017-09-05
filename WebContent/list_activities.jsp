<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Screentime Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	<div id="wrapper">
		<div id="header">
		<h2>Screentime Activities</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
		
		<!-- Put in new Add Student button -->
		
		<input type="button" value="Add Activity"
			onclick="window.location.href='add_activity_form.jsp'"; return false;
			class="add-activity-button"
		/>
		<!--  add a search box -->
		<form action="ActivityControllerServlet" method="GET">

			<input type="hidden" name="command" value="SEARCH" />

			Search activities by name: <input type="text" name="theSearchActivity" />

			<input type="submit" value="Search" class="add-activity-button" />	

		</form>
		<form action="ActivityControllerServlet" method="GET">

			<input type="hidden" name="command" value="LIST" />

			<input type="submit" value="Reset" class="add-activity-button" />	

		</form>
		<table>
		<tr>
			<th>ID</th>
			<th>Type</th>
			<th>Name</th>
			<th>Educational</th>
			<th>Actions</th>
			<th>       </th>
		</tr>
		<c:forEach var="tempActivity" items="${ACTIVITY_LIST}"> 
			<!-- set up a link for each activity (use name of object properties, not database headings -->
			<c:url var="templink" value="ActivityControllerServlet">
				<c:param name="command" value="LOAD" />
				<c:param name="activityID" value="${tempActivity.activityID}" />
			</c:url>
			<c:url var="templink2" value="ActivityControllerServlet">
				<c:param name="command" value="DELETE" />
				<c:param name="activityID" value="${tempActivity.activityID}" />
			</c:url>
				<tr>
					<td> ${tempActivity.activityID} </td>
					<td> ${tempActivity.activityType} </td>
					<td> ${tempActivity.activityName} </td>
					<td> ${tempActivity.activityEd} </td>	
					<td> <a href="${templink}">Update</a> </td>
					<td> <a href="${templink2}"
						onclick="if (!(confirm('Are you sure you want to delete the activity?'))) 
						return false">Delete</a> </td>		
				</tr>	
		</c:forEach>
		</table>
		</div>
	</div>
</body>
<div class="footer">
	<p>
		<a href="MainMenu.jsp">Back to Main Menu</a><a href="EventControllerServlet">|Go to Events</a>
	</p>
</div>
</html>