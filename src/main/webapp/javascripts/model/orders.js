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
    
    function getOrder(orderObservable, orderId) {
		var url = "order/" + orderId;
		var params = "";
		$.post(url, params, function(data) {
			orderObservable(data.data);
		});
	};

	
	function flashNewOrder(ordersObservable, clientId, order) {
		loadOrders(ordersObservable, clientId, function() {
			setTimeout(function() {
				$("#orderRow" + order.id).animate({backgroundColor: "rgba(0, 255, 0, 0.1)"});
				$("#orderRow" + order.id).animate({backgroundColor: "rgb(255, 255, 255)"});
			}, 1000);
		});
	};
	
	function flashCanceledOrder(ordersObservable, clientId, order) {
		loadOrders(ordersObservable, clientId, function() {
			setTimeout(function() {
				$("#orderRow" + order.id).animate({backgroundColor: "rgba(255, 0, 0, 0.1)"});
				$("#orderRow" + order.id).animate({backgroundColor: "rgb(255, 255, 255)"});
			}, 1000);
		});
	};
    
	var o = {
			loadOrders : loadOrders,
			getOrder : getOrder,
			cancelOrder : function(ordersObservable, clientId, order, callback) {
			$('#cancelOrderModal' + order.id).modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
	    	var url = "/orders/"+ order.id +"/cancelOrder";
			var params = "";
			$.post(url, params, function(data) {
				if (data.successful) {
					info.showInfo("Comanda a fost anulata cu succes!");
					flashCanceledOrder(ordersObservable, clientId, order);
					if (callback) {
						callback(order);
					}
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