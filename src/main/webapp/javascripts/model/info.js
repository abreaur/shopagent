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
			}, 6000);
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
			}, 6000);
		};
	
	var formatDate = function(milliseconds) {
		var monthNames = [
		                  "Ianuarie", "Februarie", "Martie",
		                  "Aprilie", "Mai", "Iunie", "Iulie",
		                  "August", "Septembrie", "Octombrie",
		                  "Noiembrie", "Decembrie"
		              ];
		var date = new Date(milliseconds);
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();

		return day + ' ' + monthNames[monthIndex] + ' ' + year;
	};
		
	var getOrderStatus = function(statusString){
		var statusMap = { "BASKET" : "CURENT\u0102",
				"PLACED" : "PLASAT\u0102",
				"CONFIRMED" : "CONFIRMAT\u0102",
				"DELIVERED" : "LIVRAT\u0102",
				"CANCELLED" : "ANULAT\u0102"
		};
		return statusMap[statusString];
	};
	
	var i = {
		"data" : data,
		"showInfo" : showInfo,
		"showError" : showError,
		"formatDate" : formatDate,
		"getOrderStatus" : getOrderStatus
	};
	
	return i;
});