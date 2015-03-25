package ro.theredpoint.shopagent.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.Client;

/**
 * @author Radu DELIU
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
	
	public Set<Client> findAll();
}
