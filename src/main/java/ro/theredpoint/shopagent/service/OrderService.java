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
	 * @param stockId
	 * @param unitOfMeasure
	 * @param quantity
	 * @return
	 */
	public Order addProduct(long clientId, long productId, long stockId, String unitOfMeasure, double quantity);
	
	/**
	 * Update quantity of an existing product.
	 * 
	 * @param clientId
	 * @param productId
	 * @param stockId
	 * @param unitOfMeasure
	 * @param quantity
	 * @return
	 * @throws BusinessException 
	 */
	public Order updateQuantity(long clientId, long productId, long stockId, String unitOfMeasure, double quantity) throws BusinessException;

	/**
	 * Update discount of an existing product.
	 * 
	 * @param clientId
	 * @param productId
	 * @param stockId
	 * @param unitOfMeasure
	 * @param quantity
	 * @return
	 * @throws BusinessException 
	 */
	public Order updateDiscount(long clientId, long productId, long stockId, String unitOfMeasure, double discount) throws BusinessException;

	/**
	 * Remove a product from active order.
	 * 
	 * @param clientId
	 * @param productId
	 * @param stockId
	 * @param unitOfMeasure
	 * @return
	 * @throws BusinessException
	 */
	public Order removeProduct(long clientId, long productId, long stockId, String unitOfMeasure) throws BusinessException;
	
	/**
	 * Place active order.
	 * 
	 * @param clientId
	 * @return
	 * @throws BusinessException 
	 */
	public Order placeActiveOrder(long clientId) throws BusinessException;
	
	/**
	 * @param clientId
	 * @return Placed customer orders.
	 */
	public Set<Order> getPlacedCustomerOrders(long clientId);
}
