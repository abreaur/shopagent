package ro.theredpoint.shopagent.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ro.theredpoint.shopagent.domain.Order;
import ro.theredpoint.shopagent.domain.Order.OrderStatus;

/**
 * @author Radu DELIU
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

	@Query(value = "from Order o where o.client.id = ?1 and o.orderStatus = ?2")
	public Order findByClientAndOrderStatus(long clientId, OrderStatus orderStatus);
}