define(['knockout', 'jQuery'], function (ko) {
    'use strict';
    
	var p = {
		getProducts : function() {
			var filters = JSON.stringify({});
			var url = "/products";
			var params = "filters="+filters;
			
			var results = ko.observableArray();
			
			$.post(url, params, function(data) {
				results(data);
			});
			
			return results;
		}	
	};
	
	return p;
});