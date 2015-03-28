package ro.theredpoint.shopagent.repository;

import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.UnitOfMeasure;

/**
 * @author Radu DELIU
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
	
	public UnitOfMeasure findByCode(String code);
}
