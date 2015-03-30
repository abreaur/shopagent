package ro.theredpoint.shopagent.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.Order;
import ro.theredpoint.shopagent.service.BusinessException;
import ro.theredpoint.shopagent.service.OrderService;
import ro.theredpoint.shopagent.web.model.OrderModel;
import ro.theredpoint.shopagent.web.model.WebResponse;

/**
 * @author Radu DELIU
 */
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	private OrderModel prepareResponse(Order order) {
		
		return new OrderModel(order);
	}
	
	private List<OrderModel> prepareResponse(List<Order> orders) {
		
		List<OrderModel> result = new ArrayList<OrderModel>();
		
		for (Order order : orders) {
			result.add(prepareResponse(order));
		}
		
		return result;
	}
	
	@RequestMapping(value = "orders/{clientId}/activeOrder", produces = "application/json")
	public OrderModel getActiveOrder(@PathVariable long clientId) {
		
		return prepareResponse(orderService.getActiveOrder(clientId));
	}
	
	@RequestMapping(value = "orders/{clientId}/addProduct", produces = "application/json")
	public WebResponse<OrderModel> addProduct(@PathVariable long clientId, @RequestParam(value = "productId") long productId,
			@RequestParam(value = "stockId") long stockId, @RequestParam(value = "unitOfMeasure") String unitOfMeasure,
			@RequestParam(value = "quantity") double quantity) {
		
		try {
			return new WebResponse<OrderModel>(prepareResponse(orderService.addProduct(clientId, productId, stockId,
					unitOfMeasure, quantity)));
		}
		catch(Exception e) {
			return new WebResponse<OrderModel>(e.getMessage());
		}
	}
	
	@RequestMapping(value = "orders/{clientId}/placeActiveOrder", produces = "application/json")
	public WebResponse<OrderModel> placeActiveOrder(@PathVariable long clientId) {
		
		try {
			return new WebResponse<OrderModel>(prepareResponse(orderService.placeActiveOrder(clientId)));
		}
		catch (Exception e) {
			return new WebResponse<OrderModel>(e.getMessage());
		}
	}
	
	@RequestMapping(value = "orders/{clientId}/updateProductQuantity", produces = "application/json")
	public WebResponse<OrderModel> updateProductQuantity(@PathVariable long clientId, @RequestParam(value = "productId") long productId,
			@RequestParam(value = "stockId") long stockId, @RequestParam(value = "unitOfMeasure") String unitOfMeasure,
			@RequestParam(value = "quantity") double quantity) {
		
		try {
			return new WebResponse<OrderModel>(prepareResponse(orderService.updateQuantity(clientId, productId, stockId,
					unitOfMeasure, quantity)));
		}
		catch(Exception e) {
			return new WebResponse<OrderModel>(e.getMessage());
		}
	}
	
	@RequestMapping(value = "orders/{clientId}/updateProductDiscount", produces = "application/json")
	public WebResponse<OrderModel> updateProductDiscount(@PathVariable long clientId, @RequestParam(value = "productId") long productId,
			@RequestParam(value = "stockId") long stockId, @RequestParam(value = "unitOfMeasure") String unitOfMeasure,
			@RequestParam(value = "discount") double discount) {
		
		try {
			return new WebResponse<OrderModel>(prepareResponse(orderService.updateDiscount(clientId, productId, stockId,
					unitOfMeasure, discount)));
		}
		catch(Exception e) {
			return new WebResponse<OrderModel>(e.getMessage());
		}
	}

	
	@RequestMapping(value = "orders/{clientId}/removeProduct", produces = "application/json")
	public WebResponse<OrderModel> removeProduct(@PathVariable long clientId, @RequestParam(value = "productId") long productId,
			@RequestParam(value = "stockId") long stockId, @RequestParam(value = "unitOfMeasure") String unitOfMeasure) {
		
		try {
			return new WebResponse<OrderModel>(prepareResponse(orderService.removeProduct(clientId, productId, stockId,
					unitOfMeasure)));
		}
		catch(Exception e) {
			return new WebResponse<OrderModel>(e.getMessage());
		}
	}

	@RequestMapping(value = "orders/{clientId}/placedOrders", produces = "application/json")
	public List<OrderModel> getPlacedOrder(@PathVariable long clientId) {
		
		return prepareResponse(orderService.getPlacedCustomerOrders(clientId));
	}
	
	@RequestMapping(value = "orders/{orderId}/cancelOrder", produces = "application/json")
	public WebResponse<OrderModel> cancelOrder(@PathVariable long orderId) {
		
		try {
			return new WebResponse<OrderModel>(prepareResponse(orderService.cancelOrder(orderId)));
		} catch (BusinessException e) {
			return new WebResponse<OrderModel>(e.getMessage());
		}
	}
}