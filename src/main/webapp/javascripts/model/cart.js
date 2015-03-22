require(['javascripts/knockout-3.2.0.js', '../../javascripts/text!../../templates/navbar.html'], function(ko, navbar) {

	function ProductsViewModel(data) {
	
		for (var i = 0; i < data.length; i++) { 
			data[i].isChecked = ko.observable(false);
		}
		
		var vm = {
	    		"products" : ko.observableArray(data)
	    	};
	    
	    var computed = {
	    
		};
	    
	    var methods = {
	        	"addToBasket" : function() {
	    			
	    		}
	    };
	    
		return {
				"vm" : vm,
				"methods" : methods,
				"computed" : computed,
				"navbar" : navbar
		};
	}

	var filters = JSON.stringify({});
	var url = "/getProducts";
	var params = "filters=" + filters;

//	$.post(url, params, function(data) {
//		ko.applyBindings(new ProductsViewModel(data));		  
//	});
	
	var data = [
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
	
	ko.applyBindings(new ProductsViewModel(data));		  
	
	$(".tabLink").removeClass("active");
	$("#cartTab").addClass("active");
});