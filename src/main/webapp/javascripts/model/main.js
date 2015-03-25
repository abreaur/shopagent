require.config({
	paths: {
	"knockout": "../knockout-3.2.0",
	"knockout-amd-helpers": "../knockout-amd-helpers",
	"text": "../text",
	"jQuery": "../jquery-1.9.0",
	"bootstrap": "../bootstrap",
	"less": "../less.min",
	"products": "products",
	"cart": "cart",
	}
});

require(['knockout',
         'products',
         'cart',
         'knockout-amd-helpers',
         'jQuery',
         'bootstrap',
         'less',],
	function(ko, products, cart) {

	ko.amdTemplateEngine.defaultPath = "../../templates";
	ko.amdTemplateEngine.defaultSuffix = ".html";
	ko.amdTemplateEngine.defaultRequireTextPluginName = "text";

	function MainViewModel(data) {
	
		var vm = {
				"navbar" : {
					"tabs": [{"id": "products", "name": "Produse"} , {"id": "cart", "name": "Cos cumparaturi"} , {"id": "orders", "name": "Comenzi"} , ],
					"selectedTab" : ko.observable("products"),
					"username" : ko.observable(data.username)
				},
				"products" : data.products,
				"orders" : ko.observableArray([]),
				"cart" : data.cart,
				"customers" : ko.observableArray([])
			};
		
		var methods = {
				"switchTab": function(tab) {
					vm.navbar.selectedTab(tab.id);
				},
				
				"addToCart" : function(model, e) {
					vm.cart.push(model);
				}
			};
		
		var computed = {
				"cartSize" : ko.computed(function(){
						return vm.cart().length;
					}
				)
			};
		
		return {
				"vm" : vm,
				"methods" : methods,
				"computed" : computed
		};
	}

	var data = {};
	data.username = "Monty Burns";
	data.products = products.getProducts(1, 10);
	data.cart = cart.getCart();
	
	ko.applyBindings(new MainViewModel(data));		  

});