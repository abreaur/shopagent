package ro.theredpoint.shopagent.repository;

import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.OrderItem;

/**
 * @author Radu DELIU
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}