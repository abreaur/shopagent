package ro.theredpoint.shopagent.repository;

import java.util.List;
import java.util.Set;

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
	
	@Query(value = "from Order o where o.client.id = ?1 and o.orderStatus <> ?2 order by created desc")
	public List<Order> findByClientAndNotOrderStatus(long clientId, OrderStatus orderStatus);
}