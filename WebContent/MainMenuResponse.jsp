<%@ page import="java.util.*" %>
<html>
<head>
	<title>Confirmation</title>
</head>

<body>
	<%
	String option = request.getParameter("screenOption");
	Cookie theCookie = new Cookie("myApp.screenOption", option);
	theCookie.setMaxAge(60*60*24*365);
	response.addCookie(theCookie);
	%>
	Option set to ${param.screenOption}
	<a href="MainMenu.html">Return to menu</a>
</body>
</html>