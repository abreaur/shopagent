require.config({
	paths: {
	"knockout": "../knockout-3.2.0",
	"knockout-amd-helpers": "../knockout-amd-helpers",
	"text": "../text",
	"jQuery": "../jquery-1.9.0",
	"bootstrap": "../bootstrap",
	"less": "../less.min"
	}
});

require(['knockout',
         'knockout-amd-helpers',
         'jQuery',
         'bootstrap',
         'less'],
	function(ko) {

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
				"products" : ko.observable(data.products),
				"orders" : ko.observableArray([]),
				"cart" : ko.observableArray([]),
				"customers" : ko.observableArray([])
			};
		
		var methods = {
				"switchTab": function(tab) {
					vm.navbar.selectedTab(tab.id);
				}
			};
		
		return {
				"vm" : vm,
				"methods" : methods
		};
	}

	var data = {};
	data.username = "Monty Burns";
	data.products = [
	            {"name" : "product1"},
	            {"name" : "product2"},
	            {"name" : "product3"},
	            {"name" : "product1"},
	            {"name" : "product2"},
	            {"name" : "product3"},
	            {"name" : "product1"},
	            {"name" : "product2"},
	            {"name" : "product3"},
	            {"name" : "product4"}
	            ];
	
	ko.applyBindings(new MainViewModel(data));		  

});