package ro.theredpoint.shopagent.service;

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
	 * @param productId
	 * @param clientId
	 * @param quantity
	 * @return
	 */
	public Order removeProduct(long productId, long clientId, double quantity);
}
