function CandidateViewModel(data) {
	var id = "";
	var firstName = "";
	var lastName = "";
	var title = "";
	var email = "";
	var phoneNo = "";
	var cvReference = "";
	
	if (data) {
		id=data.id;
		firstName = data.firstName;
		lastName = data.lastName;
		title = data.title;
		email = data.email;
		phoneNo = data.phoneNo;
		cvReference = data.cvReference;	
    }
	
	var vm = {
    		"id" : ko.observable(data.id),
    		"firstName" : ko.observable(data.firstName),
    		"lastName" : ko.observable(data.lastName),
    		"title" : ko.observable(data.title),
    		"email" : ko.observable(data.email),
    		"phone" : ko.observable(data.phone),
    		"cvReference": ko.observable(data.cvReference)
    	}
	
	var computed = {
			"cvURL" : ko.computed(function (){
				if (vm.cvReference()!="") {
					return "/viewCvFile/" + vm.cvReference();
				} else {
					return "";
				}
			})
	}
	
    var methods = {
			"saveCandidate" : function() {
				var candidate = {
					"id" : vm.id(),
					"firstName" : vm.firstName(),
					"lastName" : vm.lastName(),
					"title" : vm.title(),
					"email" : vm.email(),
					"phone" : vm.phone()
				}
				var url = "/saveCandidate";
				var params = "candidate=" + JSON.stringify(candidate);
				$.post( url, params, function( data ) {
					document.location = "/"		  
				});
			},
			"uploadCv" : function(model, e) {
			       	e.preventDefault();
			        var formData = new FormData();
			        formData.append('cvFile', $('#cvUpload')[0].files[0]);
			        formData.append('candidateId', vm.id());
			        //add uploading message
			        $.ajax({
			        	url: "/uploadCv",
			            data: formData,
			            processData: false,
			            contentType: false,
			            cache: false,
			            type: 'POST',
			            success: function(data){
			                vm.cvReference(data);
			            }
			        });
			}
    }
    
	return {"vm" : vm ,
		"methods" : methods,
		"computed" : computed};
}

function init_candidate(candidateId) {
	
	var params = "candidateId=" + candidateId;
    
	$.post( "/getCandidate", params, function( response ) {
		ko.applyBindings(new CandidateViewModel(response.data));		  
	});
}