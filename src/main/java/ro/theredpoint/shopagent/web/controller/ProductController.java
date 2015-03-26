package ro.theredpoint.shopagent.web.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.service.ProductService;

/**
 * @author Radu DELIU
 */
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "products", produces = "application/json")
	public Set<Product> getProducts() {
		return productService.getProducts();
	}
	
	@RequestMapping(value = "products/{name}", produces = "application/json")
	public Set<Product> getProductsByName(@PathVariable String name) {
		return productService.getProductsByName(name);
	}
}