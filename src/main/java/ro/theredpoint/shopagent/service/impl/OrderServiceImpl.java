package ro.theredpoint.shopagent.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ro.theredpoint.shopagent.domain.Order;
import ro.theredpoint.shopagent.domain.Order.OrderStatus;
import ro.theredpoint.shopagent.domain.OrderItem;
import ro.theredpoint.shopagent.domain.OrderItemStockUsage;
import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.domain.Stock;
import ro.theredpoint.shopagent.domain.StockConverter;
import ro.theredpoint.shopagent.domain.UnitOfMeasure;
import ro.theredpoint.shopagent.repository.ClientRepository;
import ro.theredpoint.shopagent.repository.OrderItemRepository;
import ro.theredpoint.shopagent.repository.OrderItemStockUsageRepository;
import ro.theredpoint.shopagent.repository.OrderRepository;
import ro.theredpoint.shopagent.repository.ProductRepository;
import ro.theredpoint.shopagent.repository.StockRepository;
import ro.theredpoint.shopagent.repository.UnitOfMeasureRepository;
import ro.theredpoint.shopagent.service.BusinessException;
import ro.theredpoint.shopagent.service.OrderService;
import ro.theredpoint.shopagent.service.SecurityService;

/**
 * @author Radu DELIU
 */
@Service
@Transactional
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
	private ClientRepository clientRepository;
	@Autowired
	private OrderItemStockUsageRepository orderItemStockUsageRepository; 
	
	@Override
	public Order getActiveOrder(long clientId) {

		Order order = orderRepository.findByClientAndOrderStatus(clientId, OrderStatus.BASKET);
		
		if (order == null) {
			LOG.info(String.format("No active order for client %d. Creating a new one.", clientId));
			
			order = new Order();
			
			order.setClient(clientRepository.findOne(clientId));
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
	public Order addProduct(long clientId, long productId, long stockId, String unitOfMeasure, double quantity) throws BusinessException {
		
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
			if (existingItem.getStock().getUnitOfMeasure().getCode().equals(unitOfMeasure)) {
				existingItem.setPrice(existingItem.getStock().getPrice());
			}
			else {
				
				existingItem.setPrice(getStockConverter(product, unitOfMeasure).getUnitPrice());
			}
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

	private StockConverter getStockConverter(Product product, String unitOfMeasure) throws BusinessException {

		for (StockConverter stockConverter : product.getStockConverters()) {
			if (stockConverter.getTo().getCode().equals(unitOfMeasure)) {
				
				return stockConverter;
			}
		}

		throw new BusinessException(String.format("Can not find stock convertor for product %s and unit of measure %s",
				product.getName(), unitOfMeasure));
	}

	private OrderItem findExistingItem(long productId, long stockId, String unitOfMeasure, Order order) {
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Order placeActiveOrder(long clientId) throws BusinessException {
		
		Order order = orderRepository.findByClientAndOrderStatus(clientId, OrderStatus.BASKET);
		
		if (order == null) {
			throw new BusinessException("Comanda nu poate fi plasata deoarece nu ati comanda nici un produs.");
		}
		LOG.info(String.format("Placing order %d for client %d", order.getId(), clientId));
		
		if ((order.getOrderItems() == null) || (order.getOrderItems().isEmpty())) {
			throw new BusinessException("Comanda nu poate fi plasata deoarece nu contine nici un produs.");
		}
		
		removeStock(order);
		updateCreditLimit(order, true);
		
		order.setOrderStatus(OrderStatus.PLACED);
		Calendar expectedDeliveryDate = Calendar.getInstance();
		expectedDeliveryDate.add(Calendar.HOUR_OF_DAY, 24 + 12 * order.getOrderItems().size());
		
		order.setExpectedDeliveryDate(expectedDeliveryDate.getTime());
		
		return orderRepository.save(order);
	}

	/**
	 * Removing stock usage when cancelling an order.
	 * 
	 * @param order
	 * @throws BusinessException 
	 */
	private void addStock(Order order) throws BusinessException {
		
		for (OrderItem orderItem : order.getOrderItems()) {
			
			if ((orderItem.getStockUsages() != null) && (!orderItem.getStockUsages().isEmpty())) {
				for (OrderItemStockUsage stockUsage : orderItem.getStockUsages()) {
					
					boolean convertionNeeded = !stockUsage.getUsedFrom().getUnitOfMeasure().getCode().equals(
							orderItem.getStock().getUnitOfMeasure().getCode());
					
					LOG.info(String.format("Cancelling stock usage %d for %s, order item %d. Convertion needed %b.",
							stockUsage.getId(), orderItem.getProduct().getName(), orderItem.getId(), convertionNeeded));
					
					double newQuantity = BigDecimal.valueOf(stockUsage.getUsedFrom().getQuantity()).add(
							BigDecimal.valueOf(stockUsage.getUsedQuantity())).doubleValue();
					LOG.info(String.format("Updating %s stock: old value %.2f, new value %.2f", 
							orderItem.getProduct().getName(), stockUsage.getUsedFrom().getQuantity(), newQuantity));
					stockUsage.getUsedFrom().setQuantity(newQuantity);
					
					if (!convertionNeeded) {
						stockRepository.save(stockUsage.getUsedFrom());
					}
					else {
						// Was converted, check if its stock is equal to a full unit
						StockConverter stockConverter = getStockConverter(orderItem.getProduct(),
								orderItem.getUnitOfMeasure().getCode());
						
						if (stockConverter.getRate() == stockUsage.getUsedFrom().getQuantity()) {
							// It was one product. Increasing original stock value
							
							newQuantity = BigDecimal.valueOf(orderItem.getStock().getQuantity()).add(
									BigDecimal.ONE).doubleValue();
							LOG.info(String.format("Updating %s stock: old value %.2f, new value %.2f", 
									orderItem.getProduct().getName(), orderItem.getStock().getQuantity(), newQuantity));
							orderItem.getStock().setQuantity(newQuantity);
							stockRepository.save(orderItem.getStock());
							
							// Removing stock for item
							stockUsage.getUsedFrom().setQuantity(0);
							stockRepository.save(stockUsage.getUsedFrom());
						}
						else {
							// It was not one product
							LOG.info("It was not one product");
							stockRepository.save(stockUsage.getUsedFrom());
						}
					}
					
					stockUsage.setCancelled(true);
					orderItemStockUsageRepository.save(stockUsage);
				}
			}
		}
	}
	
	/**
	 * Remove stock according to order items
	 * 
	 * @param remove
	 * @param order
	 * @throws BusinessException 
	 */
	private void removeStock(Order order) throws BusinessException {
		
		LOG.info(String.format("Updating stocks for order %d.", order.getId()));
		
		for (OrderItem orderItem : order.getOrderItems()) {
			
			if (LOG.isInfoEnabled()) {
				LOG.info(String.format("Updating stock for order item %d", orderItem.getId()));
			}
			
			double availableStock = 0;
			boolean convertStock;
			StockConverter stockConverter = null;
			
			if (orderItem.getStock().getUnitOfMeasure().equals(orderItem.getUnitOfMeasure())) {
				availableStock = orderItem.getStock().getQuantity();
				convertStock = false;
			}
			else {
				// Convert stock
				stockConverter = getStockConverter(orderItem.getProduct(), orderItem.getUnitOfMeasure().getCode());
				availableStock = BigDecimal.valueOf(orderItem.getStock().getQuantity()).multiply(BigDecimal.valueOf(
						stockConverter.getRate())).doubleValue();
				convertStock = true;
			}
			
			if (availableStock < orderItem.getQuantity()) {
				String message = String.format("Cantitatea comandata (%.2f %s) pentru produsul %s depaseste "
						+ "stocul disponibil.", orderItem.getQuantity(), orderItem.getUnitOfMeasure().getCode(),
						orderItem.getProduct().getName());
				LOG.error(message + String.format(" Stoc disponibil %s", availableStock));
				throw new BusinessException(message);
			}
			
			Stock currentStock = orderItem.getStock();
			
			if (convertStock) {
				
				Stock choosenStock = null;
				if (securityService.isClient()) {
					choosenStock = chooseStock(orderItem.getProduct(), orderItem.getUnitOfMeasure().getCode(),
							orderItem.getQuantity());
				}
				else {
					choosenStock = orderItem.getStock();
				}
				
				if (choosenStock.getUnitOfMeasure().getCode().equals(orderItem.getUnitOfMeasure().getCode())) {
					availableStock = choosenStock.getQuantity();
					// Same unit of measure, just remove quantity
					BigDecimal orderedQuantity = BigDecimal.valueOf(orderItem.getQuantity());
					double newQuantity = BigDecimal.valueOf(availableStock).subtract(orderedQuantity).doubleValue();
					LOG.info(String.format("Updating %s stock: old value %.2f, new value %.2f", 
							orderItem.getProduct().getName(), availableStock, newQuantity));
					choosenStock.setQuantity(newQuantity);
					
					addStockUsage(orderItem, choosenStock, orderedQuantity.doubleValue());
				}
				else {
					// Different units of measure
 					double requiredUnits = BigDecimal.valueOf(orderItem.getQuantity()).divide(
 							BigDecimal.valueOf(stockConverter.getRate())).doubleValue();
 					
 					int noOfUnits = (int) Math.ceil(requiredUnits);

 					// Creating used stock info
 					for (int i = 0; i < Math.floor(requiredUnits); i++) {
 						Stock stock = createNewStock(stockConverter.getProduct(), stockConverter.getUnitPrice(),
 								0, stockConverter.getTo());
 						addStockUsage(orderItem, stock, stockConverter.getRate());

 						// Removing one unit from product
 						double newQuantity = BigDecimal.valueOf(choosenStock.getQuantity()).subtract(BigDecimal.ONE).doubleValue();
 						LOG.info(String.format("Updating %s stock: old value %.2f, new value %.2f", 
 								orderItem.getProduct().getName(), choosenStock.getQuantity(), newQuantity));
 						choosenStock.setQuantity(newQuantity);
 					}
 					
 					if (requiredUnits != Math.ceil(requiredUnits)) {
 						// Not exact no of units, create a new stock info for difference
 						Stock differenceStock = createNewStock(stockConverter.getProduct(), stockConverter.getUnitPrice(),
 								BigDecimal.valueOf(stockConverter.getRate()).multiply(BigDecimal.valueOf(noOfUnits))
 									.subtract(BigDecimal.valueOf(orderItem.getQuantity())).doubleValue(),
 								stockConverter.getTo());
 						
 						// TODO implement Remove
 						LOG.info(String.format("Saving new stock info for %s, available stock %.2f %s",
 								stockConverter.getProduct().getName(), differenceStock.getQuantity(),
 								differenceStock.getUnitOfMeasure().getCode()));
 						
 						addStockUsage(orderItem, differenceStock, BigDecimal.valueOf(stockConverter.getRate()).
 								subtract(BigDecimal.valueOf(differenceStock.getQuantity())).doubleValue());
 						

 						// Removing one unit from product
 						double newQuantity = BigDecimal.valueOf(choosenStock.getQuantity()).subtract(BigDecimal.ONE).doubleValue();
 						LOG.info(String.format("Updating %s stock: old value %.2f, new value %.2f", 
 								orderItem.getProduct().getName(), choosenStock.getQuantity(), newQuantity));
 						choosenStock.setQuantity(newQuantity);
 					}
				}
			}
			else {
			
				BigDecimal orderedQuantity = BigDecimal.valueOf(orderItem.getQuantity());
				double newQuantity = BigDecimal.valueOf(availableStock).subtract(orderedQuantity).doubleValue();
				LOG.info(String.format("Updating %s stock: old value %.2f, new value %.2f", 
						orderItem.getProduct().getName(), availableStock, newQuantity));
				currentStock.setQuantity(newQuantity);
				
				addStockUsage(orderItem, currentStock, orderedQuantity.abs().doubleValue());
			}
		}
	}
	
	public Stock createNewStock(Product product, double price, double quantity, UnitOfMeasure unitOfMeasure) {
		
		Stock stock = new Stock();
			
		stock.setMain(false);
		stock.setProduct(product);
		stock.setPrice(price);
		stock.setQuantity(quantity);
		stock.setUnitOfMeasure(unitOfMeasure);
		
		return stockRepository.save(stock);
	}
	
	private void addStockUsage(OrderItem orderItem, Stock stock, double usedQuantity) {
		
		OrderItemStockUsage orderItemStockUsage = new OrderItemStockUsage();
		
		orderItemStockUsage.setUsedQuantity(usedQuantity);
		orderItemStockUsage.setOrderItem(orderItem);;
		orderItemStockUsage.setUsedFrom(stock);
		
		orderItemStockUsage = orderItemStockUsageRepository.save(orderItemStockUsage);
		
		if (orderItem.getStockUsages() == null) {
			orderItem.setStockUsages(new HashSet<OrderItemStockUsage>());
		}
		
		orderItem.getStockUsages().add(orderItemStockUsage);
	}

	/**
	 * It choose the stock info to use for client when it order using main unit of measure.
	 * 
	 * @param product
	 * @param quantity
	 * @return
	 */
	private Stock chooseStock(Product product, String unitOfMeasure, double quantity) {

		LOG.info(String.format("Choosing stock to use for %s - %.2f %s", product.getName(), quantity, unitOfMeasure));
		List<Stock> chooseFrom = new ArrayList<Stock>();
		Stock mainStock = null;
		for (Stock stock : product.getStocks()) {
			
			if ((stock.getUnitOfMeasure().getCode().equals(unitOfMeasure)) && (stock.getQuantity() >= quantity)) {
				chooseFrom.add(stock);
			}
			
			if (stock.isMain()) {
				mainStock = stock;
			}
		}
		
		if (!chooseFrom.isEmpty()) {
			Collections.sort(chooseFrom, new Comparator<Stock>() {
				public int compare(Stock o1, Stock o2) {
					return (int) (o1.getQuantity() - o2.getQuantity());
				};
			});
			
			Stock chosenStock = chooseFrom.get(0);
			
			LOG.info(String.format("Chosen stock is %d, quantity %.2f", chosenStock.getId(), chosenStock.getQuantity()));
			
			return chosenStock;
		}
		
		LOG.info(String.format("Chosen main stoc %d, quantity %.2f %s", mainStock.getId(), mainStock.getQuantity(),
				mainStock.getUnitOfMeasure().getCode()));
		
		return mainStock;
	}

	/**
	 * Update client credit limit.
	 * 
	 * @param order
	 * @param remove - credit should be decreases
	 * @throws BusinessException
	 */
	private void updateCreditLimit(Order order, boolean remove) throws BusinessException {

		LOG.info(String.format("Updating credit limit for order %d, client %d, remove %b", order.getId(),
				order.getClient().getId(), remove));

		BigDecimal orderValue = BigDecimal.valueOf(order.getAmount());
		if (remove) {
			orderValue = orderValue.multiply(BigDecimal.valueOf(-1));
		}
		
		double newCreditLimit = BigDecimal.valueOf(order.getClient().getCreditLimit()).add(
				orderValue).doubleValue();
		
		LOG.info(String.format("Updating credit limit for client %s : old value %.2f, new value %.2f", 
				order.getClient().getId(), order.getClient().getCreditLimit(), newCreditLimit));
		
		order.getClient().setCreditLimit(newCreditLimit);
		clientRepository.save(order.getClient());
	}

	@Override
	public List<Order> getPlacedCustomerOrders(long clientId) {
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
			LOG.info(String.format("Updating quantity of order item %d, new quantity %.2f", existingItem.getId(), quantity));
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
			LOG.info(String.format("Updating discount of order item %d, new discount %.2f", existingItem.getId(), discount));
			existingItem.setDiscount(discount);
		}
		
		updateOrderItemAmount(existingItem);
		
		updateOrderAmount(order);
		
		return orderRepository.save(order);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Order cancelOrder(long orderId) throws BusinessException {

		Order order = orderRepository.findOne(orderId);
		
		order.setOrderStatus(OrderStatus.CANCELLED);
		order.setCancelDate(new Date());

		addStock(order);
		updateCreditLimit(order, false);
		
		return orderRepository.save(order);
	}
}