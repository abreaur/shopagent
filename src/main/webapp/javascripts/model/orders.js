define(['knockout'], function (ko) {
    'use strict';
    
	var o = {
		loadOrders : function(ordersObservable, clientId) {
			var url = "/orders/" + clientId + "/placedOrders";
			var params = "";
			$.post(url, params, function(data) {
				ordersObservable(data);
			});
		},
		cancelOrder : function(order) {
			alert("canceling order for amount: " + order.amount);
		}
	};
	
	return o;
});