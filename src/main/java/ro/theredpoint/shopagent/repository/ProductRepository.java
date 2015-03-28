package ro.theredpoint.shopagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.Product;

/**
 * @author Radu DELIU
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	@Query(value = "SELECT p FROM Product p ORDER BY p.name")
	public List<Product> findAll();
	
	@Query(value = "SELECT p FROM Product p where p.name LIKE %?1% ORDER BY p.name")
	public List<Product> findByName(String name);
}