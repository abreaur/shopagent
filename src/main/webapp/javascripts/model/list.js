function ListViewModel(data) {
	
	var vm = {
			"candidates" : ko.observableArray(data)
    	}
    
	var methods = {
			"viewCandidate" : function(model, e) {
				var url = "/viewCv/" + model.id;
    			document.location=url;
	    	},
	    	"sendMail" : function(model, e) {
	    		var url = "/sendMail";
	    		var emails = [];
	    		for (i = 0; i < vm.candidates().length; i++) { 
    			    if (vm.candidates()[i].email) {
    			    	emails.push(vm.candidates()[i].email);
    			    }
    			}
				var params = {
						"candidateEmails" : emails, 
						"position" : "Software Engineer",
						"company" : "Amazon"
					};
				$.post( url, params, function( data ) {
					//success message
	    		});
	    	}
	}
    
	return {
		"vm" : vm ,
    	"methods" : methods
    };
}

function init_list(listId) {
	$(".tabLink").removeClass("active");
	$("#listsTab").addClass("active");
	
	var url = "/getCandidatesForList";
	var params = "listId="+listId;
	$.post( url, params, function( data ) {
		ko.applyBindings(new ListViewModel(data));
	});
}