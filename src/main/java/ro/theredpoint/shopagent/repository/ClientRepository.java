package ro.theredpoint.shopagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.Client;

/**
 * @author Radu DELIU
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
	
	@Query(value = "SELECT c FROM Client c ORDER BY c.name")
	public List<Client> findAll();
	
	@Query(value = "SELECT c FROM Client c where c.name LIKE %?1% ORDER BY c.name")
	public List<Client> findByName(String name);
}
