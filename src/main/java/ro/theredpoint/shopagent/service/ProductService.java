package ro.theredpoint.shopagent.service;

import java.util.Set;

import ro.theredpoint.shopagent.domain.Product;

/**
 * @author Radu DELIU
 */
public interface ProductService {

	public Set<Product> getProducts();
	public Set<Product> getProductsByName(String name);
}
