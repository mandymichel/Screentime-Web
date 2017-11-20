<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<head>
<title>Screen Time Menu</title>
<link rel="stylesheet" type="text/css" href="css/MainMenu.css" />
</head>
<body>
<h5>Welcome to Child Screentime Wizard, ${userName}! ${hello}!</h5>
	<div id="rightSide">
		<p id="screenParagraph">All children and teens need adequate sleep
			(8-12 hours, depending on age), physical activity (1 hour), and time
			away from media. Designate media-free times together (e.g., family
			dinner) and media-free zones (e.g., bedrooms).</p>
	</div>
	<div class="btn-group" id="leftSide">
		<form action="EventControllerServlet">
			<input class="button" type="submit" value="Events"
				name="screenOption" />
		</form>
		<c:url var="createURL" value="ReportControllerServlet">
			<c:param name="command" value="CREATE" />
		</c:url>
		<a href="${createURL}"> <input class="button" type="submit"
			value="Reports" class="screenOption" />
		</a>
		<form action="ActivityControllerServlet">
			<input class="button" type="submit" value="Activities"
				name="screenOption" />
		</form>
		<form action="VideoControllerServlet">
			<input class="button" type="submit" value="Another Video"
				name="screenOption" />
		</form>

		<p style="clear: both">
			<br>
		</p>
		<p>
			<iframe width="479" height="294"
				src="${RANDOM_VIDEO}" frameborder="0"
				allowfullscreen></iframe>
		</p>
	</div>
</body>
<div class="footer">
	<p>Child Screen Time Wizard</p>
</div>
</html>
