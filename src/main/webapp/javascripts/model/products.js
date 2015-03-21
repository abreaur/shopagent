function ProductsViewModel(data) {

	for (i = 0; i < data.length; i++) { 
		data[i].isChecked = ko.observable(false)
	}
	
	var vm = {
    		"products" : ko.observableArray(data)
    	}
    
    var computed = {
    
	}
    var methods = {
        	"addToBasket" : function() {
    			
    		}
    }
    
	return {
			"vm" : vm,
			"methods" : methods,
			"computed" : computed
	};
}

function init_products() {
	
	$(".tabLink").removeClass("active");
	$("#productsTab").addClass("active");
	
	var filters = JSON.stringify({})
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
	            {"name" : "product3"}
	            ]
	
	ko.applyBindings(new ProductsViewModel(data));		  
	
}