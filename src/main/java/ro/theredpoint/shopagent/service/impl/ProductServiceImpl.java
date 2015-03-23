package ro.theredpoint.shopagent.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.service.ProductService;
import ro.theredpoint.shopagent.service.SecurityService;

/**
 * @author Radu DELIU
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	@Autowired
	private SecurityService securityService;  
	
	private Product randomProduct() {
		Product product = new Product();
		
		product.setName("Product " + RANDOM.nextInt(100));
		product.setPicture("image" + RANDOM.nextInt(100) + ".jpg");
		product.setHasStock(RANDOM.nextBoolean());
		product.setPrice(new BigDecimal(RANDOM.nextDouble() * 100).setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		if ((product.hasStock) && (securityService.isAgent())) {
			product.setStock(RANDOM.nextInt(100));
		}
		
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