<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Create New User</title>
<link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div id="login_box">
	<form name="frmLogin" method="GET" action="LoginControllerServlet">
		<input type="hidden" name="command" value="CREATEUSER" />
		<table border="1">
			<tbody>
				<tr>
					<td>First Name:</td>
					<td><input type="text" name="firstName"></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><input type="text" name="lastName"></td>
				</tr>
				<tr>
					<td>User Name:</td>
					<td><input type="text" name="userName" placeholder="Desired User Name"/></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" placeholder="8 Character Minimum"></td>
				</tr>
			</tbody>
		</table>
		<br />
		<div>
			<input id="submitButton" type="submit" value="CREATE USER"
				class="login_button" />
		</div>
	</form>
	<c:if test="${INVALID=='1'}">
			<div class="error">
				<c:out value="${errorMsg}" />
			</div>
		</c:if>
	</div>
</body>
<div class="footer">
	<p>Child Screen Time Wizard 
	</p>
</div>
</html>