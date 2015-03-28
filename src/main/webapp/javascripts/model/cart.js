define(['info', 'knockout'], function (info, ko) {
    'use strict';
    
	var c = {
		loadCart : function(cartObservable, clientId) {
			var url = "orders/" + clientId + "/activeOrder";
			var params = "";
			$.post(url, params, function(data) {
				if (data.orderItems) {
					for (var i = 0; i < data.orderItems.length; i++) {
						if (!data.orderItems[i].discount) {
							data.orderItems[i].discount = 0;
						}
					}
				}
				cartObservable(data);
			});
		},
		
		addToCart : function(cartObservable, product, clientId) {
			var url = "orders/" + clientId + "/addProduct";
			var quantity = parseInt(product.quantity());
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
		
		updateQuantity : function(cartObservable, product, clientId) {
			var url = "orders/" + clientId + "/addProduct";
			var quantity = parseInt(product.quantity);
			var params = {
					'quantity': quantity,
					'productId': product.id
			};
			
			alert(quantity);
			
//			$.post(url, params, function(data) {
//				cartObservable(data);
//				if (quantity === 1) {
//					info.showInfo("1 produs '" + product.name + "' in valoare totala de " + quantity * product.price + " RON a fost adaugat la comanda curenta!");
//				} else {
//					info.showInfo(quantity + " produse '" + product.name + "' in valoare totala de " + quantity * product.price + " RON au fost adaugate la comanda curenta!");
//				}
//			});
		},
		
		updateProductDiscount : function(cartObservable, product, clientId) {
			var url = "orders/" + clientId + "/addProduct";
			var discount = parseInt(product.discount) / 100;
			var params = {
					'discount': discount,
					'productId': product.id
			};
			alert(discount);
//			$.post(url, params, function(data) {
//				cartObservable(data);
//				if (quantity === 1) {
//					info.showInfo("1 produs '" + product.name + "' in valoare totala de " + quantity * product.price + " RON a fost adaugat la comanda curenta!");
//				} else {
//					info.showInfo(quantity + " produse '" + product.name + "' in valoare totala de " + quantity * product.price + " RON au fost adaugate la comanda curenta!");
//				}
//			});
		},
	
		removeFromCart : function(product) {
			alert('removing product from cart: ' + product.name);
		}
	};
	
	return c;
});
