define(['info', 'orders', 'knockout'], function (info, orders, ko) {
    'use strict';
    
    var load = function(cartObservable, clientId, successCallback) {
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
    				if (successCallback) {
    					successCallback();
    				}
    			});
    		};
    
	var c = {
		loadCart : load,
		
		addToCart : function(cartObservable, product, clientId) {
			var url = "orders/" + clientId + "/addProduct";
			var selectedQuantity = 0;
			var productId = 0;
			var stockId = 0;
			var unitOfMeasure = '';
			var productName = '';
			var productPrice = '';
			
			if (product.stockSelection && product.vm && product.vm.selectedStock) {
				var stock = product.vm.selectedStock();
				productName = product.vm.name;
				productPrice = stock.price();
				selectedQuantity = parseInt(product.vm.selectedQuantity());
				productId = product.vm.id;
				stockId = stock.id();
				unitOfMeasure = stock.unitOfMeasure();
				
			} else if (product.measureSelection && product.vm && product.vm.selectedMeasure) {
				var measure = product.vm.selectedMeasure();
				productName = product.vm.name;
				productPrice = measure.price();
				selectedQuantity = parseInt(product.vm.selectedQuantity());
				productId = product.vm.id;
				stockId = product.vm.mainStockId;
				unitOfMeasure = measure.unitOfMeasure();
				
			} else {
				productName = product.name;
				productPrice = product.price;
				selectedQuantity = parseInt(product.selectedQuantity());
				productId = product.id;
				stockId = product.stockId;
				unitOfMeasure = product.unitOfMeasure;
			}
			
			var params = {
					'quantity': selectedQuantity,
					'productId': productId,
					'stockId' : stockId,
					'unitOfMeasure' : unitOfMeasure,
			};
			
			$.post(url, params, function(data) {
				if (data.successful) {
					cartObservable(data.data);
					var successMessage = selectedQuantity + " " + params.unitOfMeasure + " '" + productName + "' in valoare totala de " + params.quantity * productPrice + " RON";
					if (selectedQuantity === 1) {
						info.showInfo(successMessage + " s-a adaugat la comanda curenta!");
					} else {
						info.showInfo(successMessage + " s-au adaugat la comanda curenta!");
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
	
		removeProduct : function(cartObservable, item, clientId) {
			var url = "orders/" + clientId + "/removeProduct";
			var params = {
					'productId': item.product.id,
					'stockId' : item.stockId,
					'unitOfMeasure' : item.unitOfMeasure,
			};
			
			$.post(url, params, function(data) {
				if (data.successful) {
					cartObservable(data.data);
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
					load(cartObservable, clientId, function(){
						successCallback(data.data.id);
					});
					info.showInfo("Comanda curenta a fost plasata cu succes si va ajunge la destinatie la data: " + data.data.expectedDeliveryDate + "!");
				} else {
					var errorMessage = "Eroare la plasarea comenzii curente!";
					if (data.error) {
						errorMessage = data.error;
					}
					info.showError(errorMessage);
				}
			});
		},
	};
	
	return c;
});
