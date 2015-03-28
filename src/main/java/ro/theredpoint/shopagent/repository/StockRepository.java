package ro.theredpoint.shopagent.repository;

import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.Stock;

/**
 * @author Radu DELIU
 */
public interface StockRepository extends CrudRepository<Stock, Long> {
}