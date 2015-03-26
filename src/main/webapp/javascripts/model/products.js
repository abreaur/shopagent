define(['knockout', 'jQuery'], function (ko) {
    'use strict';
    
	var p = {
		getProducts : function() {
			var filters = JSON.stringify({});
			var url = "/products";
			var params = "filters="+filters;
			
			var results = ko.observableArray();
			
			$.post(url, params, function(data) {
				for(var i=0; i<data.length; i++) {
					data[i].quantity = 1;
				}
				results(data);
			});
			
			return results;
		}	
	};
	
	return p;
});