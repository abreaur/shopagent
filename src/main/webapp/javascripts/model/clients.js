define(['cart', 'knockout'], function (cart, ko) {
    'use strict';
    
	var c = {
		loadClients : function(clientsObservable) {
			var url = "/clients";
			var params = "";
			$.post(url, params, function(data) {
				clientsObservable(data);
			});
		},
		selectClient : function(cartObservable, clientId) {
			cart.loadCart(cartObservable, clientId);
		},
	};
	
	return c;
});