package ro.theredpoint.shopagent.web.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ro.theredpoint.shopagent.domain.Order;
import ro.theredpoint.shopagent.domain.Order.OrderStatus;
import ro.theredpoint.shopagent.domain.OrderItem;

/**
 * @author Radu DELIU
 */
public class OrderModel {

	private long id;
	private long clientId;
	private OrderStatus orderStatus;
	private Long created;
	private Long cancelDate;
	private Long expectedDeliveryDate;
	private double amount;
	private List<OrderItemModel> orderItems;
	
	public OrderModel(Order order) {
		
		this.id = order.getId();
		this.setClientId(order.getClient().getId());
		this.orderStatus = order.getOrderStatus();
		this.created = order.getCreated() != null ? order.getCreated().getTime() : null;
		this.cancelDate = order.getCancelDate() != null ? order.getCancelDate().getTime() : null;
		this.expectedDeliveryDate = order.getExpectedDeliveryDate() != null ? order.getExpectedDeliveryDate().getTime() : null;
		this.amount = order.getAmount();
		
		orderItems = new ArrayList<OrderItemModel>();
		
		if (order.getOrderItems() != null) {
			for (OrderItem orderItem : order.getOrderItems()) {
				orderItems.add(new OrderItemModel(orderItem));
			}
		}
		
		Collections.sort(orderItems);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	
	public Long getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Long cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	public Long getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Long expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public List<OrderItemModel> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemModel> orderItems) {
		this.orderItems = orderItems;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
}