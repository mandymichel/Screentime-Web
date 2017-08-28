$(document).ready(function() { 
	console.log('hi');
	
	function IsEmpty(){
		console.log("i called my function");
		var myForm = document.forms['frm'];
	    if(myForm.firstName.value === "" || myForm.startDateTime.value === "" || 
	    		myForm.endDateTime.value === "") {
		    alert("You must fill out the name, start time, and end time.");
		    return false;
		}
	    return true;
	}
	
	$("#submitButton").click(function() { 
		console.log("we clicked the thing");
		return IsEmpty();
	});
	
});
		