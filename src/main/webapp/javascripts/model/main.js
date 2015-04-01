require.config({
	paths: {
		"knockout": "../knockout-3.2.0",
		"knockout-amd-helpers": "../knockout-amd-helpers",
		"text": "../text",
		"bootstrap": "../bootstrap",
		"less": "../less.min",
		"info": "info",
		"products": "products",
		"cart": "cart",
		"security": "security",
		"clients": "clients",
		"product": "product",
		"orders": "orders",
	}
});

require(['knockout',
         'info',
         'products',
         'cart',
         'security',
         'clients',
         'product',
         'orders',
         'knockout-amd-helpers',
         'bootstrap',
         'less',],
	function(ko, info, products, cart, security, clients, product, orders) {

	ko.amdTemplateEngine.defaultPath = "../../templates";
	ko.amdTemplateEngine.defaultSuffix = ".html";
	ko.amdTemplateEngine.defaultRequireTextPluginName = "text";

	function MainViewModel(data) {
	
		var vm = {
				"filterString": "",
				"clientsFilterString": "",
				"products" : ko.observable(data.products),
				"orders" : ko.observableArray([]),
				"userData" : data.userData,
				"cartData" : data.cartData,
				"clientsData" : data.clientsData,
				"ordersData" : data.ordersData,
				"customers" : ko.observableArray([]),
				"info" : data.info,
				"selectedProductId" : ko.observable(""),
				"selectedProduct" : ko.observable({}),
				"selectedClientDetailsId" : ko.observable(""),
				"selectedClientDetails" : ko.observable({})
			};
		
		var computed = {
				"cartSize" : ko.computed(function() {
						var length = 0;
						if (vm.cartData().orderItems) {
							length = vm.cartData().orderItems.length;
						}
						return length;
					}
				),
				"isAgent" : ko.computed(function() {
						var isAgent = false;
						if (vm.userData().roles) {
							var roles = vm.userData().roles;
							for(var i = 0; i < roles.length; i++) {
								if (roles[i].name === 'AGENT') {
									isAgent = true;
									break;
								}
							}
						}
						return isAgent;
					}
				),
			};

		var navbar = {
				"tabs": ko.computed(function() {
					var tabs = [{"id": "products", "name": "Produse"} , 
						         {"id": "cart", "name": "Comanda curenta"} , 
						         {"id": "orders", "name": "Comenzi"}
						         ];
					if (computed.isAgent()) {
				         tabs.push({"id": "clients", "name": "Clienti"});
					}
					return tabs;
				}),
				"selectedTab" : ko.observable("products"),
				"selectedClient" : ko.observable({}),
				"selectedClientCreditLimit" : ko.observable(),
		};
		
		var switchTab = function(tab) {
			navbar.selectedTab(tab.id);
			vm.selectedProductId("");
			vm.selectedClientDetailsId("");
		};
		
		var methods = {
				"switchTab": switchTab,
				"viewCart": function(model, e) {
					navbar.selectedTab('cart');
					vm.selectedProductId("");
					vm.selectedClientDetailsId("");
				},
				"viewClients": function(model, e) {
					navbar.selectedTab('clients');
					vm.selectedProductId("");
					vm.selectedClientDetailsId("");
				},
				"addToCart" : function(model, e) {
					e.stopPropagation();
					if (computed.isAgent()) {
						model.stockSelection = true;
					} else {
						model.measureSelection = true;
					}
					cart.addToCart(vm.cartData, model, data.cartData().clientId);
				},
				"updateQuantity" : function(model, e) {
					e.stopPropagation();
					cart.updateQuantity(vm.cartData, model, data.cartData().clientId);
				},
				"updateProductDiscount" : function(model, e) {
					e.stopPropagation();
					cart.updateProductDiscount(vm.cartData, model, data.cartData().clientId);
				},
				"removeProduct" : function(model, e) {
					e.stopPropagation();
					cart.removeProduct(vm.cartData, model, data.cartData().clientId);
				},
				"placeActiveOrder" : function(model, e) {
					e.stopPropagation();
					cart.placeActiveOrder(vm.cartData, model, data.cartData().clientId, function(order) {
						navbar.selectedTab('orders');
						orders.flashNewOrder(vm.ordersData, data.cartData().clientId, order);
						if (computed.isAgent()) {
							navbar.selectedClientCreditLimit(navbar.selectedClientCreditLimit() - order.amount);
						}
						vm.products(products.getProducts(vm.filterString));
					});
				},
				"cancelOrder" : function(model, e) {
					orders.cancelOrder(vm.ordersData, data.cartData().clientId, model, function(order) {
						vm.products(products.getProducts(vm.filterString));
						if (computed.isAgent()) {
							navbar.selectedClientCreditLimit(navbar.selectedClientCreditLimit() + order.amount);
						}
					});
				},
				"selectClient" : function(model, e) {
					clients.selectClient(vm.cartData, vm.ordersData, model.id);
					navbar.selectedClient(model);
					navbar.selectedClientCreditLimit(model.creditLimit);
				},
				"selectClientFromTable" : function(model, e) {
					e.stopPropagation();
					clients.selectClient(vm.cartData, vm.ordersData, model.id);
					navbar.selectedClient(model);
					navbar.selectedClientCreditLimit(model.creditLimit);
					var oldColor = $(".clientSelector").css("background-color");
					$(".clientSelector").animate({backgroundColor: "rgba(0, 255, 0, 0.2)"});
					$(".clientSelector").animate({backgroundColor: oldColor});
				},
				"filterProducts" : function(model, e) {
					vm.products(products.getProducts(vm.filterString));
				},
				"filterClients" : function(model, e) {
					clients.loadClients(vm.clientsData, vm.clientsFilterString);
				},
				"isCartEmpty" : ko.computed(function() {
					var length = 0;
					if (vm.cartData().orderItems) {
						length = vm.cartData().orderItems.length;
					}
					if (length == 0 && navbar.selectedTab() === 'cart') {
						navbar.selectedTab('products');
					}
					return length == 0;
				}),
				"selectProduct" : function(model, e) {
					e.stopPropagation();
					vm.selectedProduct(new product.ProductViewModel(model));
					vm.selectedProductId(model.id);
					navbar.selectedTab("");
				},
				"selectClientDetails" : function(model, e) {
					e.stopPropagation();
					vm.selectedClientDetails(model);
					vm.selectedClientDetailsId(model.id);
					navbar.selectedTab("");
				},
				"stopPropagation" : function(model, e) {
					e.stopPropagation();
				}
			};
		
		return {
				"vm" : vm,
				"methods" : methods,
				"computed" : computed,
				"navbar" : navbar,
		};
	}

	var userObservable = ko.observable({});
	var cartObservable = ko.observable({});
	var clientsObservable = ko.observable({});
	clients.loadClients(clientsObservable, "");
	
	var ordersObservable = ko.observable({});
	security.loadCurrentUser(userObservable, cartObservable, ordersObservable);
	
	var data = {
			"userData" : userObservable,
			"cartData" : cartObservable,
			"clientsData" : clientsObservable,
			"ordersData" : ordersObservable,
			"products" : products.getProducts(""),
			"info" : info.data,
	};
	
	ko.bindingHandlers.clearinput = {
		    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	var value = valueAccessor();
		        var valueUnwrapped = ko.unwrap(value);

		        if (valueUnwrapped) {
		        	$(element).click(function(){
		        		var attachedInput = $(element).next(":input");
		        		attachedInput.val('');
		        		attachedInput.change();
		        	});
		        }		 
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    }
		};
	
	ko.bindingHandlers.touchspin = {
		    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	var value = valueAccessor();
		        var valueUnwrapped = ko.unwrap(value);

		        if (valueUnwrapped === 'small') {
		        	$(element).TouchSpin({
			            verticalbuttons: true
			        });
		        } else if (valueUnwrapped === 'percent') {
		        	$(element).TouchSpin({
		                min: 0,
		                max: 100,
		                step: 1,
		                boostat: 5,
		                maxboostedstep: 10,
		                postfix: '%'
		            });
		        } else {
		        	$(element).TouchSpin({
		                postfix: valueUnwrapped,
		                max: 1000,
		            });
		        }
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	
		    }
		};

	ko.bindingHandlers.colorValueIndicator = {
		    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	var value = valueAccessor();
		        var valueUnwrapped = ko.unwrap(value);
		        
		        if (valueUnwrapped) {
			        if (valueUnwrapped >= 0.7) {
			        	$(element).css("background-color", "rgba(0, 255, 0, 0.2)");
			        } else if (valueUnwrapped >= 0.5) {
			        	$(element).css("background-color", "rgba(255, 255, 0, 0.2)");
			        } else {
			        	$(element).css("background-color", "rgba(255, 0, 0, 0.2)");
			        }
		        } else {
		        	$(element).css("background-color", "#EEE");
		        }
		    }
		};

	ko.bindingHandlers.googleMap = {
		    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	var value = valueAccessor();
		        var valueUnwrapped = ko.unwrap(value);
		        
		        if (valueUnwrapped) {
		        	var mapOptions = {
		      	          zoom: 14
		      	        };
		        	geocoder = new google.maps.Geocoder();
		        	var map = new google.maps.Map(element, mapOptions);
		        	geocoder.geocode( { 'address': valueUnwrapped}, function(results, status) {
		        	      if (status == google.maps.GeocoderStatus.OK) {
		        	        map.setCenter(results[0].geometry.location);
		        	        var marker = new google.maps.Marker({
		        	            map: map,
		        	            position: results[0].geometry.location
		        	        });
		        	      } else {
		        	        alert('Geocode was not successful for the following reason: ' + status);
		        	      }
		        	    });
		        }
		    }
		};
	
	ko.applyBindings(new MainViewModel(data));
});