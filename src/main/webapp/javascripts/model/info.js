define(['knockout', 'jQuery'], function (ko) {
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
		};

	var showError = function(message) {
			data.message(message);
			data.isVisible(true);
			data.info(false);
			data.error(true);
		};

	
	var i = {
		"data" : data,
		"showInfo" : showInfo,
		"showError" : showError
	};
	
	return i;
});