<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<body>
		<head>
		<h2>Average Screentime per Day</h2>
		<c:set var = "firstName" scope = "session" value = "${CHILDNAME}"/>
		<c:out value = "${CHILDNAME}"/>
		</head>
		<br/>
		<br/>
      	 <c:set var = "average" scope = "session" value = "${CHILDAVERAGE}"/>
      	<c:out value = "${CHILDAVERAGE}"/>
	</body>
</html>