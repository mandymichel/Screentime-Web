<!DOCTYPE html>
<html>
<head>
<title>
Update Activity
</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-activity-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Screentime Activities</h2>
		</div>
	</div>
	<div id="container">
		<h3>Update Activity</h3>
		<form action="ActivityControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE"/>
			<input type="hidden" name="activityID" value="${THE_ACTIVITY.activityID}"/>
			<table>
				<tbody>
					<tr>
					<td><label>Type(game, video, conversation):</label></td>
					<td><input type="radio" name="activityType" value="game" checked="checked"/>Game</td>
					<td><input type="radio" name="activityType" value="video"/>Video</td>
					<td><input type="radio" name="activityType" value="conversation"/>Conversation</td>
					</tr>
					<tr>
					<td><label>Name(e.g. Moana):</label></td>
					<td><input type="text" name="activityName" value="${THE_ACTIVITY.activityName}"/></td>
					</tr>
					<tr>
					<td><label>Educational(y/n):</label></td>
					<td>
					<input type="radio" name="activityEd" value="y" checked="checked"/>Yes
					</td>
					<td>
					<input type="radio" name="activityEd" value="n"/>No
					</td>
					</tr>
					<tr>
					<td><label></label></td>
					<td><input type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
			</form>
	<div style="clear: both;"></div>
	<p>
		<a href="ActivityControllerServlet">Back to List</a>
	</p>
	</div>
</body>
</html>