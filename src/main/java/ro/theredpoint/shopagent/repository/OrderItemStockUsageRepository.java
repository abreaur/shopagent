package ro.theredpoint.shopagent.repository;

import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.OrderItemStockUsage;

/**
 * @author Radu DELIU
 */
public interface OrderItemStockUsageRepository extends CrudRepository<OrderItemStockUsage, Long> {
}