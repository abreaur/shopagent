define(['cart', 'orders', 'knockout'], function (cart, orders, ko) {
    'use strict';
    
	var c = {
		loadClients : function(clientsObservable, searchString) {
			var url = "/clients/" + searchString;
			var params = "";
			$.post(url, params, function(data) {
				clientsObservable(data);
			});
		},
		selectClient : function(cartObservable, ordersObservable, clientId) {
			cart.loadCart(cartObservable, clientId);
			orders.loadOrders(ordersObservable, clientId);
		},
	};
	
	return c;
});