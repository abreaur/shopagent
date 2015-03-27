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
				"info" : data.info
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
				},
				"viewCart": function(model, e) {
					navbar.selectedTab('cart');
				},
				"viewClients": function(model, e) {
					navbar.selectedTab('clients');
				},
				"addToCart" : function(model, e) {
					cart.addToCart(vm.cartData, model, data.cartData().client.id);
				},
				"selectClient" : function(model, e) {
					clients.selectClient(vm.cartData, model.id);
					navbar.selectedClient(model.name);
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
		        $(element).TouchSpin();
		    },
		    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
		    	
		    }
		};
	
	ko.applyBindings(new MainViewModel(data));
});