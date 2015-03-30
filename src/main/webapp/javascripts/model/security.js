define(['cart', 'orders', 'knockout'], function (cart, orders, ko) {
    'use strict';
    
	var u = {
		loadCurrentUser : function(userObservable, cartObservable, ordersObservable) {
			var url = "security/currentUser";
			var params = "";
			
			$.post(url, params, function(data) {
				var isClient = false;
				userObservable(data);
				if (data.roles) {
					for(var i = 0; i < data.roles.length; i++) {
						if (data.roles[i].name === 'CLIENT') {
							isClient = true;
							break;
						}
					}
				}
				
				if (isClient) {
					cart.loadCart(cartObservable, data.id);
					orders.loadOrders(ordersObservable, data.id);
				}
			});
		}
	};
	
	return u;
});