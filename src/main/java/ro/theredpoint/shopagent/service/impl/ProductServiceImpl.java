package ro.theredpoint.shopagent.service.impl;

import java.util.List;

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

	@Autowired
	private SecurityService securityService;  
	
	@Autowired
	private ProductRepository productRepository; 
	
	public List<Product> getProducts() {
		
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public Product getProduct(long id) {
		return productRepository.findOne(id);
	}
}