define(['info', 'knockout'], function (info, ko) {
    'use strict';
    
	var c = {
		loadCart : function(cartObservable, clientId) {
			var url = "orders/" + clientId + "/activeOrder";
			var params = "";
			$.post(url, params, function(data) {
				cartObservable(data);
			});
		},
	
		addToCart : function(cartObservable, product, clientId, successCallback) {
			var url = "orders/" + clientId + "/addProduct";
			var quantity = parseInt(product.quantity);
			var params = {
					'quantity': quantity,
					'productId': product.id
			};
			$.post(url, params, function(data) {
				cartObservable(data);
				if (quantity === 1) {
					info.showInfo("1 produs '" + product.name + "' in valoare totala de " + quantity * product.price + " RON a fost adaugat la comanda curenta!");
				} else {
					info.showInfo(quantity + " produse '" + product.name + "' in valoare totala de " + quantity * product.price + " RON au fost adaugate la comanda curenta!");
				}
			});
		},
	
		removeFromCart : function(product) {
			alert('removing product from cart: ' + product.name);
		}
	};
	
	return c;
});
