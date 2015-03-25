package ro.theredpoint.shopagent.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.Product;

/**
 * @author Radu DELIU
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	public Set<Product> findAll();
}