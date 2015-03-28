package ro.theredpoint.shopagent.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Order;
import ro.theredpoint.shopagent.domain.Order.OrderStatus;
import ro.theredpoint.shopagent.domain.OrderItem;
import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.repository.OrderItemRepository;
import ro.theredpoint.shopagent.repository.OrderRepository;
import ro.theredpoint.shopagent.repository.ProductRepository;
import ro.theredpoint.shopagent.repository.StockRepository;
import ro.theredpoint.shopagent.repository.UnitOfMeasureRepository;
import ro.theredpoint.shopagent.service.BusinessException;
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
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;
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
	public Order addProduct(long clientId, long productId, long stockId, String unitOfMeasure, double quantity) {
		
		Order order = getActiveOrder(clientId);
		OrderItem existingItem = findExistingItem(productId, stockId, unitOfMeasure, order);
		
		if (existingItem == null) {
			LOG.info(String.format("Product %d does not exists on order %d. Creating a new order item.",
					productId, order.getId()));
			
			existingItem = new OrderItem();
			Product product = productRepository.findOne(productId);
			existingItem.setProduct(product);
			existingItem.setDiscount(0);
			existingItem.setQuantity(quantity);
			existingItem.setStock(stockRepository.findOne(stockId));
			existingItem.setPrice(existingItem.getStock().getPrice());
			existingItem.setOrder(order);
			existingItem.setUnitOfMeasure(unitOfMeasureRepository.findByCode(unitOfMeasure));
			
			order.getOrderItems().add(existingItem);
		}
		else {
			LOG.info(String.format("Product %d exists on order %d. Adding new quantity", productId, order.getId()));
			existingItem.setQuantity(BigDecimal.valueOf(existingItem.getQuantity()).add(BigDecimal.valueOf(quantity)).doubleValue());
		}
		
		updateOrderItemAmount(existingItem);
		
		updateOrderAmount(order);
		
		return orderRepository.save(order);
	}

	private OrderItem findExistingItem(long productId, long stockId,
			String unitOfMeasure, Order order) {
		OrderItem existingItem = null;
		
		for (OrderItem orderItem : order.getOrderItems()) {
			if ((orderItem.getProduct().getId() == productId) && (orderItem.getStock().getId() == stockId)
					 && (orderItem.getUnitOfMeasure().getCode().equals(unitOfMeasure))) {
				existingItem = orderItem;
				break;
			}
		}
		return existingItem;
	}

	private void updateOrderItemAmount(OrderItem orderItem) {
		
		orderItem.setAmount(BigDecimal.valueOf(orderItem.getQuantity()).multiply(BigDecimal.valueOf(
				orderItem.getPrice())).multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(
				orderItem.getDiscount()).divide(BigDecimal.valueOf(100)))).doubleValue());
	}
	
	private void updateOrderAmount(Order order) {

		BigDecimal amount = BigDecimal.ZERO;
		
		for (OrderItem orderItem : order.getOrderItems()) {

			amount = amount.add(BigDecimal.valueOf(orderItem.getAmount()));
		}
		
		order.setAmount(amount.doubleValue());
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

	@Override
	public Order updateQuantity(long clientId, long productId, long stockId, String unitOfMeasure, double quantity) throws BusinessException {

		Order order = getActiveOrder(clientId);
		OrderItem existingItem = findExistingItem(productId, stockId, unitOfMeasure, order);
		
		if (existingItem == null) {
			LOG.error(String.format("Product %s, stockId: %d, unitOfMeasure %s does not exists on order %d of client %d",
					productId, stockId, unitOfMeasure, order.getId(), clientId));
			throw new BusinessException("Articolul selectat nu exista pe comanda");
		}
		else {
			LOG.info(String.format("Updating quantity of order item %d, new quantity %f", existingItem.getId(), quantity));
			existingItem.setQuantity(quantity);
		}
		
		updateOrderItemAmount(existingItem);
		
		updateOrderAmount(order);
		
		return orderRepository.save(order);
	}

	@Override
	public Order removeProduct(long clientId, long productId, long stockId, String unitOfMeasure) throws BusinessException {
		
		Order order = getActiveOrder(clientId);
		OrderItem existingItem = findExistingItem(productId, stockId, unitOfMeasure, order);
		
		if (existingItem == null) {
			LOG.error(String.format("Product %s, stockId: %d, unitOfMeasure %s does not exists on order %d of "
					+ "client %d. Nothing to remove",
					productId, stockId, unitOfMeasure, order.getId(), clientId));
			return order;
		}

		LOG.info(String.format("Removing item %d from order %d", existingItem.getId(), order.getId()));
		
		order.getOrderItems().remove(existingItem);
		orderItemRepository.delete(existingItem);
		updateOrderAmount(order);
		
		return orderRepository.save(order);
	}

	@Override
	public Order updateDiscount(long clientId, long productId, long stockId, String unitOfMeasure, double discount) throws BusinessException {

		Order order = getActiveOrder(clientId);
		OrderItem existingItem = findExistingItem(productId, stockId, unitOfMeasure, order);
		
		if (existingItem == null) {
			LOG.error(String.format("Product %s, stockId: %d, unitOfMeasure %s does not exists on order %d of client %d",
					productId, stockId, unitOfMeasure, order.getId(), clientId));
			throw new BusinessException("Articolul selectat nu exista pe comanda");
		}
		else {
			LOG.info(String.format("Updating discount of order item %d, new discount %f", existingItem.getId(), discount));
			existingItem.setDiscount(discount);
		}
		
		updateOrderItemAmount(existingItem);
		
		updateOrderAmount(order);
		
		return orderRepository.save(order);
	}
}