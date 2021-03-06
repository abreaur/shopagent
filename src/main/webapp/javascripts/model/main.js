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
				"selectedOrderId" : ko.observable(""),
				"selectedOrder" : ko.observable({}),
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
						         {"id": "cart", "name": "Comanda curent\u0103"} , 
						         {"id": "orders", "name": "Comenzi"}
						         ];
					if (computed.isAgent()) {
				         tabs.push({"id": "clients", "name": "Clien\u021Bi"});
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
			vm.selectedOrderId("");
			vm.selectedClientDetailsId("");				
		};
		
		var selectClient = function(model, e) {
			clients.selectClient(vm.cartData, vm.ordersData, model.id);
			navbar.selectedClient(model);
			navbar.selectedClientCreditLimit(model.creditLimit);
		};
		
		var flashClientSelection = function() {
			var oldColor = $(".clientSelector").css("background-color");
			$(".clientSelector").animate({backgroundColor: "rgba(0, 255, 0, 0.2)"});
			$(".clientSelector").animate({backgroundColor: oldColor});
		};
		
		var methods = {
				"switchTab": switchTab,
				"viewProducts": function(model, e) {
					navbar.selectedTab('products');
					vm.selectedProductId("");
					vm.selectedOrderId("");
					vm.selectedClientDetailsId("");
				},
				"viewCart": function(model, e) {
					if (vm.cartData().orderItems.length > 0) {
						navbar.selectedTab('cart');
						vm.selectedProductId("");
						vm.selectedOrderId("");
						vm.selectedClientDetailsId("");
					}
				},
				"viewClients": function(model, e) {
					if (vm.clientsData().length > 0) {
						navbar.selectedTab('clients');
						vm.selectedProductId("");
						vm.selectedOrderId("");
						vm.selectedClientDetailsId("");
					}
				},
				"viewOrders": function(model, e) {
					if (vm.ordersData().length > 0) {
						navbar.selectedTab('orders');
						vm.selectedProductId("");
						vm.selectedOrderId("");
						vm.selectedClientDetailsId("");
					}
				},
				"selectAndViewOrders": function(model, e) {
					selectClient(model);
					flashClientSelection();
					navbar.selectedTab('orders');
					vm.selectedProductId("");
					vm.selectedOrderId("");
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
					e.preventDefault();
					e.stopPropagation();
					orders.cancelOrder(vm.ordersData, data.cartData().clientId, model, function(order) {
						vm.products(products.getProducts(vm.filterString));
						if (computed.isAgent()) {
							navbar.selectedClientCreditLimit(navbar.selectedClientCreditLimit() + order.amount);
						}
					});
				},
				"viewOrder" : function(model, e) {
					e.stopPropagation();
					orders.getOrder(vm.selectedOrder, model.id);
					vm.selectedOrderId(model.id);
					navbar.selectedTab("");
				},
				"selectClient" : selectClient,
				"selectClientFromTable" : function(model, e) {
					e.stopPropagation();
					clients.selectClient(vm.cartData, vm.ordersData, model.id);
					navbar.selectedClient(model);
					navbar.selectedClientCreditLimit(model.creditLimit);
					flashClientSelection();
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
				"viewClientDetails" : function(model, e) {
					e.stopPropagation();
					if (model.contacts){
						model.contacts = model.contacts.sort(function(a, b) {
							   var aType = a.contactType;
							   var bType = b.contactType;
							   return (aType === 'MAIN') ? -1 : 1;
							});
					}
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
	
	Number.prototype.formatMoney = function(c, d, t){
		var n = this, 
		    c = isNaN(c = Math.abs(c)) ? 2 : c, 
		    d = d == undefined ? "," : d, 
		    t = t == undefined ? "." : t, 
		    s = n < 0 ? "-" : "", 
		    i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", 
		    j = (j = i.length) > 3 ? j % 3 : 0;
		   return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
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
	
	ko.bindingHandlers.formattedDate = {
		    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	var value = valueAccessor();
		        var valueUnwrapped = ko.unwrap(value);
		        
		        if (valueUnwrapped) {
			        	$(element).html(info.formatDate(valueUnwrapped));
		        } else {
		        	$(element).html("");
		        }
		    }
		};
	
	ko.bindingHandlers.orderStatus = {
		    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	var value = valueAccessor();
		        var valueUnwrapped = ko.unwrap(value);
		        
		        if (valueUnwrapped) {
			        	$(element).html(info.getOrderStatus(valueUnwrapped));
		        } else {
		        	$(element).html("");
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