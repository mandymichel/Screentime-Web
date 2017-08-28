<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body>
<head>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {

       //figure out how to put actual month names in here
        var data = google.visualization.arrayToDataTable([
          ['number', 'Monthly Minutes'],
           <c:forEach var="dataIntValue" items="${TIMEMONTHLYYEAR}">
                ['Month', ${dataIntValue}],
            </c:forEach>		
        ]);   

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));

        chart.draw(data, {width: 600, height: 340, min: 0});
      }
    </script>
<h2>Time on Educational Screens Monthly for a Year</h2>
<c:set var="firstName" scope="session" value="${CHILDNAME}" />
<c:out value="${CHILDNAME}" />
</head>
<br />
<div id="chart_div" style="width: 600px; height: 340px;">This is
	just a replacement in case Javascript is not available or used for SEO
	purposes</div>
<br />
<c:set var="average" scope="session" value="${TIMEMONTHLYYEAR}" />
<c:out value="${TIMEMONTHLYYEAR}" />
<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="MainMenu.jsp">Main Menu</a></li>
		<li class="breadcrumb-item"><a href="screen_report.jsp">Report</a></li>
		<li class="breadcrumb-item active">Screentime Monthly for a Year</li>
	</ol>
</body>
</html>