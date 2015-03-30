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
				"products" : ko.observable(data.products),
				"orders" : ko.observableArray([]),
				"userData" : data.userData,
				"cartData" : data.cartData,
				"clientsData" : data.clientsData,
				"ordersData" : data.ordersData,
				"customers" : ko.observableArray([]),
				"info" : data.info,
				"selectedProductId" : ko.observable(""),
				"selectedProduct" : ko.observable({})
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
				"selectedClient" : ko.observable({})
		};
		
		var methods = {
				"switchTab": function(tab) {
					navbar.selectedTab(tab.id);
					vm.selectedProductId("");
				},
				"viewCart": function(model, e) {
					navbar.selectedTab('cart');
					vm.selectedProductId("");
				},
				"viewClients": function(model, e) {
					navbar.selectedTab('clients');
					vm.selectedProductId("");
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
					cart.placeActiveOrder(vm.cartData, model, data.cartData().clientId, function(orderId) {
						navbar.selectedTab('orders');
						orders.flashNewOrder(vm.ordersData, data.cartData().clientId, orderId);
						vm.products(products.getProducts(vm.filterString));
					});
				},
				"cancelOrder" : function(model, e) {
					e.stopPropagation();
					orders.cancelOrder(vm.ordersData, data.cartData().clientId, model.id);
				},
				"selectClient" : function(model, e) {
					clients.selectClient(vm.cartData, vm.ordersData, model.id);
					navbar.selectedClient(model);
				},
				"filterProducts" : function(model, e) {
					vm.products(products.getProducts(vm.filterString));
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
	clients.loadClients(clientsObservable);
	
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
	
	ko.applyBindings(new MainViewModel(data));
});