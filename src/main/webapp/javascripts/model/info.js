define(['knockout'], function (ko) {
    'use strict';
	var data = {
					"message" : ko.observable(""),
					"error" : ko.observable(false),
					"info" : ko.observable(false),
					"isVisible" : ko.observable(false)
		};

	var showInfo = function(message) {
			data.message(message);
			data.isVisible(true);
			data.info(true);
			data.error(false);
			setTimeout(function() { 
				$( ".informationContainer" ).slideUp(1000, function() {
					data.isVisible(false);
				});
			}, 5000);
		};

	var showError = function(message) {
			data.message(message);
			data.isVisible(true);
			data.info(false);
			data.error(true);
			setTimeout(function() { 
				$( ".informationContainer" ).slideUp(1000, function() {
					data.isVisible(false);
				});
			}, 5000);
		};
	
	var i = {
		"data" : data,
		"showInfo" : showInfo,
		"showError" : showError
	};
	
	return i;
});