function JobsViewModel(data) {
	
	var vm = {
			
			"jobs": ko.observableArray(data),
			"selected" : ko.observable(0),
			"selectedJob" : ko.observable({"lists": []}),
			"description": ko.observable(""),
			"newListName": ko.observable("")
    	}
    
	var methods = {
    		"selectJob" : function(model, e) {
    			model.selected = true;
    			vm.selected(model.id);
    			vm.selectedJob(model);
    			vm.description(model.description);
    		},
    		"listView" : function(model, e) {
    			var url = "/listView/" + model.id;
    			document.location=url;
    		},
    		"saveNewList" : function(model, e) {
    			var url = "/saveList";
				var params = {
					"listName" : vm.newListName(), 
					"jobId"	: vm.selected()
				};
				$.post( url, params, function( data ) {
					vm.selectedJob().lists.push(data);
					$('#createListModal').modal('hide');
				});
    		}
	}
    
	return {"vm" : vm ,
    		"methods" : methods};
}

function init_jobs(tab) {
	$(".tabLink").removeClass("active");
	$("#" + tab).addClass("active");

	var url = "/getJobs";

	$.post( url, function( data ) {
		ko.applyBindings(new JobsViewModel(data));		  
	});
}