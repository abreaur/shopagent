define(['knockout'], function (ko) {
    'use strict';

    function loadOrders(ordersObservable, clientId, callback) {
    	var url = "/orders/" + clientId + "/placedOrders";
		var params = "";
		$.post(url, params, function(data) {
			ordersObservable(data);
			if (callback) {
				callback();
			}
		});
	}
    
	var o = {
		loadOrders : loadOrders,
		cancelOrder : function(order) {
			alert("canceling order for amount: " + order.amount);
		},
		flashNewOrder : function(ordersObservable, clientId, orderId) {
			loadOrders(ordersObservable, clientId, function() {
				setTimeout(function() {
					$("#orderRow" + orderId).animate({backgroundColor: "rgba(0, 255, 0, 0.1)"});
					$("#orderRow" + orderId).animate({backgroundColor: "rgb(255, 255, 255)"});
				}, 1000);
			});
		}
	};
	
	return o;
});