package ro.theredpoint.shopagent.service;

import java.util.List;

import ro.theredpoint.shopagent.domain.Product;

/**
 * @author Radu DELIU
 */
public interface ProductService {

	public List<Product> getProducts();
	public List<Product> getProductsByName(String name);
	public Product getProduct(long id);
}
