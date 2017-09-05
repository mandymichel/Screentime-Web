<!DOCTYPE html>
<html>
<head>
<title>
Add Activity
</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-activity-style.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous" 
  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" ></script>
  <script src="./js/activity_project.js"> </script>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Screentime Activities</h2>
		</div>
	</div>
	<div id="container">
		<h3>Add Activity</h3>
		<form action="ActivityControllerServlet" name="frm" method="POST">
			<input type="hidden" name="command" value="ADD"/>
			<table>
				<tbody>
					<tr>
					<td><label>Type:</label></td>
					<td><input type="radio" name="activityType" value="game" checked="checked"/>Game
					<br>
					<input type="radio" name="activityType" value="video"/>Video
					<br>
					<input type="radio" name="activityType" value="conversation"/>Conversation
					</td>
					</tr>
					<br>
					<tr>
					<td><label>Name:</label></td>
					<td><input type="text" name="activityName" placeholder="e.g. Star Wars"/></td>
					</tr>
					<tr>
					<td><label>Educational:</label></td>
					<td>
					<input type="radio" name="activityEd" value="y" checked="checked"/>Yes
					<br>
					<input type="radio" name="activityEd" value="n"/>No
					</td>
					</tr>
					<tr>
					<td><label></label></td>
					<td><input id="activitySubmitButton" type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
			</form>
	<div style="clear: both;"></div>
	<p>
		<a href="ActivityControllerServlet">Back to Activity List</a>
	</p>
	</div>
</body>
</html>