<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
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

	<c:url var="createURL" value="LoginControllerServlet">
		<c:param name="command" value="REGISTER" />
	</c:url>
	<a href="${createURL}"> <input type="button" value="Register"
		class="register-button" />
	</a>
	<div id="login_box">
		<form name="frmLogin" method="GET" action="LoginControllerServlet">
			<input type="hidden" name="command" value="LOGIN" />
			<table border="1">
				<tbody>
					<tr>
						<td>User Name:</td>
						<td><input type="text" name="userName" /></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="password"></td>
					</tr>
				</tbody>
			</table>
			<br />
			<div>
				<input id="submitButton" type="submit" value="Login"
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
	<p>Child Screen Time Wizard</p>
</div>
</html>