package ro.theredpoint.shopagent.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.Product;

/**
 * @author Radu DELIU
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	public Set<Product> findAll();
	
	@Query(value = "SELECT p FROM Product p where p.name LIKE %?1%")
	public Set<Product> findByName(String name);
}