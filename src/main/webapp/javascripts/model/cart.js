define(['info', 'knockout'], function (info, ko) {
    'use strict';
    
    var load = function(cartObservable, clientId) {
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
    		};
    
	var c = {
		loadCart : load,
		
		addToCart : function(cartObservable, product, clientId) {
			var url = "orders/" + clientId + "/addProduct";
			var selectedQuantity = parseInt(product.selectedQuantity());
			var params = {
					'quantity': selectedQuantity,
					'productId': product.id,
					'stockId' : product.stockId,
					'unitOfMeasure' : product.unitOfMeasure,
			};
			$.post(url, params, function(data) {
				if (data.successful) {
					cartObservable(data.data);
					if (selectedQuantity === 1) {
						info.showInfo("1 produs '" + product.name + "' in valoare totala de " + selectedQuantity * product.price + " RON a fost adaugat la comanda curenta!");
					} else {
						info.showInfo(selectedQuantity + " produse '" + product.name + "' in valoare totala de " + selectedQuantity * product.price + " RON au fost adaugate la comanda curenta!");
					}
				} else {
					info.showError("Eroare la adaugare produs!");
					console.log(data.error);
				}
			});
		},
		
		updateQuantity : function(cartObservable, item, clientId) {
			var url = "orders/" + clientId + "/updateProductQuantity";
			var quantity = parseInt(item.quantity);
			var params = {
					'quantity': quantity,
					'productId': item.product.id,
					'stockId' : item.stockId,
					'unitOfMeasure' : item.unitOfMeasure,
			};
			
			$.post(url, params, function(data) {
				if (data.successful) {
					cartObservable(data.data);
				} else {
					info.showError("Eroare la modificarea cantitatii!");
					console.log(data.error);
				}
			});
		},
		
		updateProductDiscount : function(cartObservable, item, clientId) {
			var url = "orders/" + clientId + "/updateProductDiscount";
			var discount = Math.min(100, parseInt(item.discount));
			var params = {
					'discount': discount,
					'productId': item.product.id,
					'stockId' : item.stockId,
					'unitOfMeasure' : item.unitOfMeasure,
			};
			$.post(url, params, function(data) {
				if (data.successful) {
					cartObservable(data.data);
				} else {
					info.showError("Eroare la modificarea discountului!");
					console.log(data.error);
				}
			});
		},
	
		removeProduct : function(cartObservable, item, clientId, callback) {
			var url = "orders/" + clientId + "/removeProduct";
			var params = {
					'productId': item.product.id,
					'stockId' : item.stockId,
					'unitOfMeasure' : item.unitOfMeasure,
			};
			
			$.post(url, params, function(data) {
				if (data.successful) {
					cartObservable(data.data);
					callback();
				} else {
					info.showError("Eroare la eliminarea produsului din comanda curenta!");
					console.log(data.error);
				}
			});
		},
	
		placeActiveOrder : function(cartObservable, item, clientId, successCallback) {
			var url = "orders/" + clientId + "/placeActiveOrder";
			var params = {};
			
			$.post(url, params, function(data) {
				if (data.successful) {
					cartObservable(data.data);
					load(cartObservable, clientId);
					successCallback();
					info.showInfo("Comanda curenta a fost plasata cu succes si va ajunge la destinatie la data: " + data.data.expectedDeliveryDate + "!");
				} else {
					var errorMessage = "Eroare la plasarea comenzii curente!";
					if (data.error) {
						erorMessage = data.error;
					}
					info.showError(errorMessage);
					console.log(errorMessage);
				}
			});
		},
	};
	
	return c;
});
