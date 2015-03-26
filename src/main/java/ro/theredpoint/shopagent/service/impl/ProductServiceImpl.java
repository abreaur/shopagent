package ro.theredpoint.shopagent.service.impl;

import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.repository.ProductRepository;
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
	
	@Autowired
	private ProductRepository productRepository; 
	
	private void randomProductInfo(Product product) {
		
		product.setHasStock(RANDOM.nextBoolean());
		
		if ((product.hasStock) && (securityService.isAgent())) {
//			product.setStock(RANDOM.nextInt(100));
		}
	}

	public Set<Product> getProducts() {
		
		Set<Product> products = productRepository.findAll();
		
		for (Product product : products) {
			
			randomProductInfo(product);
		}
		
		return products;
	}

	@Override
	public Set<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}
}