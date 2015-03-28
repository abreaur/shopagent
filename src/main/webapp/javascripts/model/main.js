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
	}
});

require(['knockout',
         'info',
         'products',
         'cart',
         'security',
         'clients',
         'knockout-amd-helpers',
         'bootstrap',
         'less',],
	function(ko, info, products, cart, security, clients) {

	ko.amdTemplateEngine.defaultPath = "../../templates";
	ko.amdTemplateEngine.defaultSuffix = ".html";
	ko.amdTemplateEngine.defaultRequireTextPluginName = "text";

	function MainViewModel(data) {
	
		var vm = {
				"products" : data.products,
				"orders" : ko.observableArray([]),
				"userData" : data.userData,
				"cartData" : data.cartData,
				"clientsData" : data.clientsData,
				"customers" : ko.observableArray([]),
				"info" : data.info,
				"selectedProductId" : ko.observable(""),
				"selectedProduct" : ko.observable({
					
				})
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
						         {"id": "orders", "name": "Comenzi"}];
					if (computed.isAgent()) {
				         tabs.push({"id": "clients", "name": "Clienti"});
					}
					return tabs;
				}),
				"selectedTab" : ko.observable("products"),
				"selectedClient" : ko.observable("")
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
				"selectClient" : function(model, e) {
					clients.selectClient(vm.cartData, model.id);
					navbar.selectedClient(model.name);
				},
				"selectProduct" : function(model, e) {
//					e.stopPropagation();
//					vm.selectedProduct(model);
//					vm.selectedProductId(model.id);
//					navbar.selectedTab("");
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
	
	security.loadCurrentUser(userObservable, cartObservable);
	
	var data = {
			"userData" : userObservable,
			"cartData" : cartObservable,
			"clientsData" : clientsObservable,
			"products" : products.getProducts(1, 10),
			"info" : info.data,
	};
	
	ko.bindingHandlers.touchspin = {
		    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	var value = valueAccessor();
		        var valueUnwrapped = ko.unwrap(value);

		        if (valueUnwrapped === 'normal') {
		        	$(element).TouchSpin();
		        } else if (valueUnwrapped === 'small') {
		        	$(element).TouchSpin({
			            verticalbuttons: true,
			            verticalupclass: 'glyphicon glyphicon-plus',
			            verticaldownclass: 'glyphicon glyphicon-minus'
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
		        }
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	
		    }
		};
	
	ko.applyBindings(new MainViewModel(data));
});