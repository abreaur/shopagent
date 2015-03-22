package ro.theredpoint.shopagent.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.service.ProductService;

/**
 * @author Radu DELIU
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	private Product randomProduct() {
		Product product = new Product();
		
		product.setName("Product " + RANDOM.nextInt(100));
		product.setPicture("image" + RANDOM.nextInt(100) + ".jpg");
		product.setHasStock(RANDOM.nextBoolean());
		product.setPrice(RANDOM.nextDouble() * 100);
		
		return product;
	}
	
	public List<Product> getProducts() {
		
		List<Product> products = new ArrayList<Product>();
		
		for (int i = 0; i < 10; i++) {
			products.add(randomProduct());
		}
		
		return products;
	}
}