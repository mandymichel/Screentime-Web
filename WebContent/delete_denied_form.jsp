<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<body>
		<head>
		Sorry, you can't delete that activity.
		</head>
		<br/>
      	 <c:set var = "average" scope = "session" value = "${TIMEMONTHLYYEAR}"/>
      	 Activity #
      	<c:out value = "${THE_ACTIVITY_ID}"/> is being used in a screen event.
      	<p>
			<a href="ActivityControllerServlet">Back to Activity List</a>
		</p>
	</body>
</html>