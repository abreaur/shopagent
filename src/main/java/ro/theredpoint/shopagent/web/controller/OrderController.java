package ro.theredpoint.shopagent.web.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.Order;
import ro.theredpoint.shopagent.domain.OrderItem;
import ro.theredpoint.shopagent.service.OrderService;

/**
 * @author Radu DELIU
 */
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	private Order prepareResponse(Order order) {
		
		if (order.getOrderItems() != null) {
			for (OrderItem orderItem : order.getOrderItems()) {
				orderItem.setOrder(null);
				orderItem.getProduct().setStocks(null);
				orderItem.getProduct().setStockConverters(null);
			}
		}
		
		return order;
	}
	
	private Set<Order> prepareResponse(Set<Order> orders) {
		
		Set<Order> result = new HashSet<Order>();
		
		for (Order order : orders) {
			result.add(prepareResponse(order));
		}
		
		return result;
	}
	
	@RequestMapping(value = "orders/{clientId}/activeOrder", produces = "application/json")
	public Order getActiveOrder(@PathVariable long clientId) {
		
		return prepareResponse(orderService.getActiveOrder(clientId));
	}
	
	@RequestMapping(value = "orders/{clientId}/addProduct", produces = "application/json")
	public Order addProduct(@PathVariable long clientId, @RequestParam(value = "productId") long productId,
			@RequestParam(value = "quantity") double quantity,
			@RequestParam(value = "discount", defaultValue = "0", required = false) double discount) {
		
		return prepareResponse(orderService.addProduct(clientId, productId, quantity, discount));	
	}
	
	@RequestMapping(value = "orders/{clientId}/placeActiveOrder", produces = "application/json")
	public Order placeActiveOrder(@PathVariable long clientId) {
		
		return prepareResponse(orderService.placeActiveOrder(clientId));
	}
	
	@RequestMapping(value = "orders/{clientId}/placedOrders", produces = "application/json")
	public Set<Order> getPlacedOrder(@PathVariable long clientId) {
		
		return prepareResponse(orderService.getPlacedCustomerOrders(clientId));
	}
}