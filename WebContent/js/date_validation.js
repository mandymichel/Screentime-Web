$(document).ready(function() { 
	console.log('hi');

	$.getScript("./js/moment.js",function(){
		//script attempting to use momentjs starts below
		function validateTime() {
			var dateInput = document.forms['frm'];
			var moment1 = moment(dateInput.startDateTime.value, "DD-MM-YYYYTHH:mm");
			var moment2 = moment(dateInput.endDateTime.value,"DD-MM-YYYYTHH:mm");
			if (moment1.isSameOrAfter(moment2)) {
				alert("Start date must be before end date");
				return false;
			}
			return true;
		}
		$("#submitButton").click(function() { 
			console.log("we clicked the thing");
			return validateTime();
		});
		
	
});
});		

