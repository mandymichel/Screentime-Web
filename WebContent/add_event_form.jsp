<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Event</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-event-style.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous" 
  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" ></script>
  <script src="./js/project.js"> </script>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Screentime Events</h2>
		</div>
	</div>

	<div id="container">
		<h3>Add Event</h3>
		
		<form action="EventControllerServlet" name="frm" method="POST">
			<input type="hidden" name="command" value="ADD" />
			<table>
				<tbody>
					<tr>
						<td><label>Activities:</label></td>
						<td>
							<select name="actIDString">
								<c:forEach var="tempActivity" items="${ACTIVITY_LIST}">
									<option value="${tempActivity.getActivityID()}">${tempActivity.getActivityName() }</option> 
								</c:forEach>
							</select>
						</td>
					</tr>			
					
					<tr>
						<td><label>Child's Name:</label></td>
						<td><input type="text" name="firstName" placeholder="First Name"/></td>
							
					</tr>
					<tr>
						<td><label>Notes:</label></td>
						<td><input type="text" name="notes" placeholder="Description, rating, etc."/></td>
					</tr>
					
					<tr>
						<td><label>Start (date and time):</label></td>
						<td><input type="datetime-local" name="startDateTime" /></td>
					</tr>				
					<tr>
						<td><label>End (date and time):</label></td>
						<td><input type="datetime-local" name="endDateTime" /></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input id="submitButton" type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		<p>
			<a href="EventControllerServlet">Back to Event List</a>
		</p>
	</div>
	
</body>
</html>