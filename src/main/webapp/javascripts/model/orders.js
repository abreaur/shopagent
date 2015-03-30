define(['knockout', 'info'], function (ko, info) {
    'use strict';

    function loadOrders(ordersObservable, clientId, callback) {
    	var url = "/orders/" + clientId + "/placedOrders";
		var params = "";
		$.post(url, params, function(data) {
			ordersObservable(data);
			if (callback) {
				callback();
			}
		});
	};
    
	function flashNewOrder(ordersObservable, clientId, orderId) {
		loadOrders(ordersObservable, clientId, function() {
			setTimeout(function() {
				$("#orderRow" + orderId).animate({backgroundColor: "rgba(0, 255, 0, 0.1)"});
				$("#orderRow" + orderId).animate({backgroundColor: "rgb(255, 255, 255)"});
			}, 1000);
		});
	};
	
	function flashCanceledOrder(ordersObservable, clientId, orderId) {
		loadOrders(ordersObservable, clientId, function() {
			setTimeout(function() {
				$("#orderRow" + orderId).animate({backgroundColor: "rgba(255, 0, 0, 0.1)"});
				$("#orderRow" + orderId).animate({backgroundColor: "rgb(255, 255, 255)"});
			}, 1000);
		});
	};
    
	var o = {
		loadOrders : loadOrders,
		cancelOrder : function(ordersObservable, clientId, orderId) {
	    	var url = "/orders/"+ orderId +"/cancelOrder";
			var params = "";
			$.post(url, params, function(data) {
				if (data.successful) {
					info.showInfo("Comanda a fost anulata cu succes!");
					flashCanceledOrder(ordersObservable, clientId, orderId);
				} else {
					var errorMessage = "Eroare la anularea comenzii!";
					if (data.error) {
						errorMessage = data.error;
					}
					info.showError(errorMessage);
				}
			});
		},
		flashNewOrder: flashNewOrder,
		flashCanceledOrder: flashCanceledOrder,
	};
	
	return o;
});