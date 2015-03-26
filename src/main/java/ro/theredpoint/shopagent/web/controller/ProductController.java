package ro.theredpoint.shopagent.web.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.service.ProductService;
import ro.theredpoint.shopagent.web.model.ProductModel;

/**
 * @author Radu DELIU
 */
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	private Set<ProductModel> convertToModel(Set<Product> products) {
		
		Set<ProductModel> productModels = new HashSet<ProductModel>();
		
		for (Product product : products) {
			productModels.add(new ProductModel(product));
		}
		
		return productModels;
	}
	
	@RequestMapping(value = "products", produces = "application/json")
	public Set<ProductModel> getProducts() {
		return convertToModel(productService.getProducts());
	}
	
	@RequestMapping(value = "products/{name}", produces = "application/json")
	public Set<ProductModel> getProductsByName(@PathVariable String name) {
		return convertToModel(productService.getProductsByName(name));
	}
}