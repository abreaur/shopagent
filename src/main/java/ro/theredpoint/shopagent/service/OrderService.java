package ro.theredpoint.shopagent.service;

import java.util.Set;

import ro.theredpoint.shopagent.domain.Order;

/**
 * @author Radu DELIU
 */
public interface OrderService {

	/**
	 * @param clientId
	 * @return active order. If no order exists a new one will be created.
	 */
	public Order getActiveOrder(long clientId);
	
	/**
	 * Add a new product to current order.
	 * 
	 * @param clientId
	 * @param productId
	 * @param quantity
	 * @param discount
	 * @return
	 */
	public Order addProduct(long clientId, long productId, double quantity, double discount);
	
	/**
	 * Remove a product from active order.
	 * 
	 * @param clientId
	 * @param productId
	 * @param quantity
	 * @return
	 */
	public Order removeProduct(long clientId, long productId, double quantity);
	
	/**
	 * Place active order.
	 * 
	 * @param clientId
	 * @return
	 */
	public Order placeActiveOrder(long clientId);
	
	/**
	 * @param clientId
	 * @return Placed customer orders.
	 */
	public Set<Order> getPlacedCustomerOrders(long clientId);
}
