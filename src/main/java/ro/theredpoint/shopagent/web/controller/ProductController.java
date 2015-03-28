package ro.theredpoint.shopagent.web.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<ProductModel> convertToModel(List<Product> products) {
		
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		
		for (Product product : products) {
			productModels.add(new ProductModel(product, true));
		}
		
		return productModels;
	}
	
	@RequestMapping(value = "products", produces = "application/json")
	public List<ProductModel> getProducts() {
		return convertToModel(productService.getProducts());
	}
	
	@RequestMapping(value = "products/{name}", produces = "application/json")
	public List<ProductModel> getProductsByName(@PathVariable String name) {
		return convertToModel(productService.getProductsByName(name));
	}
	
	@RequestMapping(value = "product/{productId}", produces = "application/json")
	public ProductModel getProduct(@PathVariable long productId) {
		
		return new ProductModel(productService.getProduct(productId), true);
	}
}