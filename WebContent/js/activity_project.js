$(document).ready(function() { 
	console.log('hi');
	
	function IsEmpty(){
		console.log("i called my function");
		var myForm = document.forms['frm'];
	    if (myForm.activityName.value === "") {
	    		alert("You must name the activity.");
	    		return false;
	    }
	    return true;
	}
	
	$("#activitySubmitButton").click(function() { 
		console.log("we clicked the thing");
		return IsEmpty();
	});
});
		