package ro.theredpoint.shopagent.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Product> getProducts() {
		return productService.getProducts();
	}
	
}