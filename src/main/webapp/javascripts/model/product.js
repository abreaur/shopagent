define([ 'knockout' ], function(ko) {
	'use strict';

	var p = {
		"ProductViewModel" : function(data) {
			var stocks = [];
			var mainStock = {};
			for (var i = 0; i < data.stocks.length; i++) {
				var stock = {
						"id" : ko.observable(data.stocks[i].id),
						"unitOfMeasure" : ko.observable(data.stocks[i].unitOfMeasure),
						"price" : ko.observable(data.stocks[i].price),
						"quantity" : ko.observable(data.stocks[i].quantity),
						"main" : ko.observable(data.stocks[i].main),
						"value" : data.stocks[i].id + " / " + data.stocks[i].unitOfMeasure + " / " + data.stocks[i].main,
						"text": data.stocks[i].quantity + ' ' + data.stocks[i].unitOfMeasure + ',  ' + data.stocks[i].price + ' RON / ' + data.stocks[i].unitOfMeasure
					};
				if (stock.main()) {
					mainStock = stock;
				};
				stocks.push(stock);
			}
			
			var measureUnits = [];
			var measures = [];
			var mainMeasure = {};
			
			for (var i = 0; i < data.stocks.length; i++) {
				var measure = {
						"unitOfMeasure" : ko.observable(data.stocks[i].unitOfMeasure),
						"price" : ko.observable(data.stocks[i].price),
						"value" : data.stocks[i].unitOfMeasure,
						"text": data.stocks[i].price + ' RON / ' + data.stocks[i].unitOfMeasure
					};
				if (data.stocks[i].main) {
					mainMeasure = measure;
				};
				if (measureUnits.indexOf(measure.value) === -1) {
					measureUnits.push(measure.value);
					measures.push(measure);
				}
			}
			
			var vm = {
					"picture" : data.picture,
					"price" : data.price,
					"unitOfMeasure" : data.unitOfMeasure,
					"hasStock" : data.hasStock,
					"mainStockId" : data.stockId,
					"name" : data.name,
					"id" : data.id,
					"selectedQuantity" : ko.observable(1),
					"stocks" : stocks,
					"selectedStock": ko.observable(mainStock),
					"selectedStockValue": ko.observable(mainStock.value),
					"measures" : measures,
					"selectedMeasure": ko.observable(mainMeasure),
					"selectedMeasureValue": ko.observable(mainMeasure.value)
			};
			
			var computed = {
			};

			var methods = {
					"changeSelectedStock" : function(model, e) {
						for (var i = 0; i < vm.stocks.length; i++) {
							if (vm.stocks[i].value === model.value) {
								vm.selectedStock(model);
								break;
							}
						}
					},
					"changeSelectedMeasure" : function(model, e) {
						for (var i = 0; i < vm.measures.length; i++) {
							if (vm.measures[i].value === model.value) {
								vm.selectedMeasure(model);
								break;
							}
						}
					}
			};
			
			return {
					"vm" : vm,
					"methods" : methods,
					"computed" : computed,
			};
		}
	};
	
	return p;
});