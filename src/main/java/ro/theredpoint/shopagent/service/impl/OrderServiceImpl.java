package ro.theredpoint.shopagent.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Order;
import ro.theredpoint.shopagent.domain.OrderItem;
import ro.theredpoint.shopagent.domain.Order.OrderStatus;
import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.repository.OrderRepository;
import ro.theredpoint.shopagent.repository.ProductRepository;
import ro.theredpoint.shopagent.service.ClientService;
import ro.theredpoint.shopagent.service.OrderService;
import ro.theredpoint.shopagent.service.SecurityService;

/**
 * @author Radu DELIU
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private ClientService clientService;
	
	@Override
	public Order getActiveOrder(long clientId) {

		Order order = orderRepository.findByClientAndOrderStatus(clientId, OrderStatus.BASKET);
		
		if (order == null) {
			LOG.info(String.format("No active order for client %d. Creating a new one.", clientId));
			
			order = new Order();
			
			order.setClient(clientService.getClient(clientId));
			order.setUser(securityService.getCurrentUser());
			order.setOrderStatus(OrderStatus.BASKET);
			order.setCreated(new Date());
			
			order = orderRepository.save(order);
			
			LOG.info(String.format("Order %d created for client %d", order.getId(), clientId));
		}
		else {
			LOG.info(String.format("Order %d loaded for client %d", order.getId(), clientId));
		}
		
		return order;
	}

	@Override
	public Order addProduct(long clientId, long productId, double quantity, double discount) {
		
		Order order = getActiveOrder(clientId);
		
		OrderItem existingItem = null;
		
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem.getProduct().getId() == productId) {
				existingItem = orderItem;
				break;
			}
		}
		
		if (existingItem == null) {
			LOG.info(String.format("Product %d does not exists on order %d. Creating a new order item.",
					productId, order.getId()));
			
			existingItem = new OrderItem();
			Product product = productRepository.findOne(productId);
			existingItem.setProduct(product);
			existingItem.setDiscount(discount);
			existingItem.setQuantity(quantity);
			existingItem.setPrice(product.getPrice());
			existingItem.setOrder(order);
			
			order.getOrderItems().add(existingItem);
		}
		else {
			LOG.info(String.format("Product %d exists on order %d. Adding new quantity", productId, order.getId()));
			existingItem.setQuantity(BigDecimal.valueOf(existingItem.getQuantity()).add(BigDecimal.valueOf(quantity)).doubleValue());
		}
		
		// TODO use discount
		// Update amount
		existingItem.setAmount(BigDecimal.valueOf(quantity).multiply(BigDecimal.valueOf(
				existingItem.getPrice())).doubleValue());
		
		
		return orderRepository.save(order);
	}

	@Override
	public Order removeProduct(long clientId, long productId, double quantity) {
		return null;
	}

	@Override
	public Order placeActiveOrder(long clientId) {
		
		Order order = orderRepository.findByClientAndOrderStatus(clientId, OrderStatus.BASKET);
		
		if (order == null) {
			// TODO No active order - Raise error
			return null;
		}
		
		order.setOrderStatus(OrderStatus.PLACED);
		Calendar expectedDeliveryDate = Calendar.getInstance();
		expectedDeliveryDate.add(Calendar.HOUR_OF_DAY, 24 + 12 * order.getOrderItems().size());
		
		order.setExpectedDeliveryDate(expectedDeliveryDate.getTime());
		
		return orderRepository.save(order);
	}

	@Override
	public Set<Order> getPlacedCustomerOrders(long clientId) {
		return orderRepository.findByClientAndNotOrderStatus(clientId, OrderStatus.BASKET);
	}
}