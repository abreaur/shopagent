require.config({
	paths: {
	"knockout": "../knockout-3.2.0",
	"knockout-amd-helpers": "../knockout-amd-helpers",
	"text": "../text",
	"jQuery": "../jquery-1.9.0",
	"touchspin": "../touchspin",
	"bootstrap": "../bootstrap",
	"less": "../less.min",
	"products": "products",
	"cart": "cart",
	"security": "security",
	}
});

require(['knockout',
         'products',
         'cart',
         'security',
         'knockout-amd-helpers',
         'jQuery',
         'touchspin',
         'bootstrap',
         'less',],
	function(ko, products, cart, security) {

	ko.amdTemplateEngine.defaultPath = "../../templates";
	ko.amdTemplateEngine.defaultSuffix = ".html";
	ko.amdTemplateEngine.defaultRequireTextPluginName = "text";

	function MainViewModel(data) {
	
		var vm = {
				"navbar" : {
					"tabs": [{"id": "products", "name": "Produse"} , {"id": "cart", "name": "Cos cumparaturi"} , {"id": "orders", "name": "Comenzi"} , ],
					"selectedTab" : ko.observable("products")
				},
				"products" : data.products,
				"orders" : ko.observableArray([]),
				"userData" : data.userData,
				"cartData" : data.cartData,
				"customers" : ko.observableArray([])
			};
		
		var methods = {
				"switchTab": function(tab) {
					vm.navbar.selectedTab(tab.id);
				},
				"viewCart": function(model, e) {
					vm.navbar.selectedTab('cart');
				},
				"addToCart" : function(model, e) {
					cart.addToCart(vm.cartData, model, data.cartData().client.id);
				}
			};
		
		var computed = {
				"cartSize" : ko.computed(function() {
						var length = 0;
						if (vm.cartData().orderItems) {
							length = vm.cartData().orderItems.length;
						}
						return length;
					}
				)
			};
		
		return {
				"vm" : vm,
				"methods" : methods,
				"computed" : computed
		};
	}

	var userObservable = ko.observable({});
	var cartObservable = ko.observable({});
	
	security.loadCurrentUser(userObservable, cartObservable);
	
	var data = {
			"userData" : userObservable,
			"cartData" : cartObservable,
			"products" : products.getProducts(1, 10)
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