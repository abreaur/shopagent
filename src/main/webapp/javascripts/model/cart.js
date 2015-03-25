define(['knockout', 'jQuery'], function (ko) {
    'use strict';
    
	var c = {
		getCart : function() {
//			var filters = JSON.stringify({});
//			var url = "/products";
//			var params = "filters="+filters;
//			
//			var results = ko.observableArray();
//			
//			$.post(url, params, function(data) {
//				results(data);
//			});
//			
//			return results;
			
			var products = [
			                {'name' : 'some product id', 'quantity' : 1, 'price' : 12},
			                {'name' : 'other product id', 'quantity' : 3, 'price' : 3},
			                {'name' : 'third product id', 'quantity' : 2, 'price' : 32.5},
			                {'name' : 'last product id', 'quantity' : 1, 'price' : 10},
			                ];
			
			
			return ko.observableArray(products);
		},
	
		addToCart : function(product) {
			alert('added product to cart: ' + product.name + ' price: ' + product.price);
		},
	
		removeFromCart : function(product) {
			alert('removing product from cart: ' + product.name);
		}
	};
	
	return c;
});