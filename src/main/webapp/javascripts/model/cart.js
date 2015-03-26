define(['knockout', 'jQuery'], function (ko) {
    'use strict';
    
	var c = {
		loadCart : function(cartObservable, clientId) {
			var url = "/orders/" + clientId + "/activeOrder";
			var params = "";
			$.post(url, params, function(data) {
				cartObservable(data);
			});
		},
	
		addToCart : function(cartObservable, product, clientId, successCallback) {
			var url = "/orders/" + clientId + "/addProduct";
			var params = {
					quantity: parseInt(product.quantity),
					productId: product.id
			};
			$.post(url, params, function(data) {
				cartObservable(data);
			});
		},
	
		removeFromCart : function(product) {
			alert('removing product from cart: ' + product.name);
		}
	};
	
	return c;
});