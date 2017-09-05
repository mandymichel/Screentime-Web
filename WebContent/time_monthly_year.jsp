<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {

       //figure out how to put actual month names in here
        var dataTable = [
          ['number', 'Monthly Minutes'],
          	<c:forEach var="dataIntValue" items="${TIMEMONTHLYYEAR}" varStatus="loop">
			    ['${MONTHS.get(loop.index)}', ${dataIntValue}],      
             </c:forEach>		
        ];
        console.log(dataTable);
        var data = google.visualization.arrayToDataTable(dataTable);  
       	

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));

        chart.draw(data, {width: 600, height: 340, min: 0});
      }
    </script>
</head>
<body>
	<h2>Time on Screens Monthly for a Year</h2>
	<c:set var="firstName" scope="session" value="${CHILDNAME}" />
	<c:out value="${CHILDNAME}" />
	<br />
	<div id="chart_div" style="width: 600px; height: 340px;">This is
		just a replacement in case Javascript is not available or used for SEO
		purposes</div>
	<c:set var="average" scope="session" value="${TIMEMONTHLYYEAR}" />
	<c:out value="${TIMEMONTHLYYEAR}" />
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="MainMenu.jsp">Main Menu</a></li>
		<li class="breadcrumb-item"><a href="screen_report.jsp">Report</a></li>
		<li class="breadcrumb-item active">Screentime Monthly for a Year</li>
	</ol>
</body>
</html>