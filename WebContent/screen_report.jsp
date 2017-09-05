<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body>
<head>
<title>Screentime Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/reportstyle.css">
</head>
<div id="wrapper">
	<div id="header">
		<h2>Screentime Reports</h2>
	</div>
</div>
<div id="container">
	<h3>Create Report</h3>
	<form action="ReportControllerServlet" method="POST">
		<input type="hidden" name="command" value="DISPLAY" />
		<table>
			<tbody>
				<tr>
					<td><label>Children:</label></td>
					<td><select name="firstName">
							<c:forEach var="tempChild" items="${CHILD_LIST}">
								<option value="${tempChild}">${tempChild}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><label>Type of Report:</label></td>
					<td><select name="reportType">
							<option value="averageTimePerDay">Average Time Per Day on Screens</option>
							<option value="averageTimePerDayUneducational">Average Time Per Day on Uneducational Screens</option>
							<option value="averageTimePerDayEducational">Average Time Per Day on Educational Screens</option>
							<option value="timeMonthlyForYear">Total Screentime Monthly for a Year</option>
							<option value="timeEducationalMonthlyForYear">Total Educational Screentime Monthly for a Year</option>
							<option value="timeUneducationalMonthlyForYear">Total Uneducational Screentime Monthly for a Year</option>
					</select></td>
				</tr>
				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Create Report"
						class=".create_report_button" /></td>
				</tr>
			</tbody>
		</table>
	</form>
	<div style="clear: both;"></div>
</div>
<div class=footer>
	<p>
		<a href="MainMenu.jsp">Back to Main Menu</a>
	</p>
</div>
</body>
</html>