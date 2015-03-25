define(['knockout', 'jQuery'], function (ko) {
    'use strict';
    
	var c = {
		getCurrentUser : function() {
			var url = "/security/currentUser";
			var params = "";
			
			var currentUser = ko.observable({});
			
			$.post(url, params, function(data) {
				currentUser(data);
			});
			
			return currentUser;
		}
	};
	
	return c;
});