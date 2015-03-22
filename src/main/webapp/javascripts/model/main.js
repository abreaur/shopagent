require.config({
	paths: {
	"knockout": "../knockout-3.2.0",
	"knockout-amd-helpers": "../knockout-amd-helpers",
	"text": "../text"
	}
});

require(['knockout',
         'knockout-amd-helpers'],
	function(ko) {

	ko.amdTemplateEngine.defaultPath = "../../templates";
	ko.amdTemplateEngine.defaultSuffix = ".html";
	ko.amdTemplateEngine.defaultRequireTextPluginName = "text";

	function MainViewModel(data) {
	
		return {
				"test" : "some text",
				"vm" : {
					"username" : ko.observable(data.username),
					"products" : data.products,
					"orders" : ko.observableArray([]),
					"cart" : ko.observableArray([]),
					"customers" : ko.observableArray([])
				}
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

	$(".tabLink").removeClass("active");
	$("#productsTab").addClass("active");
	
});