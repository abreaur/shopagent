define([ 'knockout' ], function(ko) {
	'use strict';

	var p = {
		getProducts : function(searchString) {
			var url = "products/" + searchString;
			var params = {};

			var results = ko.observableArray();

			$.post(url, params, function(data) {
				for (var i = 0; i < data.length; i++) {
					data[i].selectedQuantity = ko.observable(1);
				}
				results(data);
			});

			return results;
		}
	};

	return p;
});