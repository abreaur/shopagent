define(['cart', 'knockout'], function (cart, ko) {
    'use strict';
    
	var u = {
		loadCurrentUser : function(userObservable, cartObservable) {
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
				}
			});
		}
	};
	
	return u;
});