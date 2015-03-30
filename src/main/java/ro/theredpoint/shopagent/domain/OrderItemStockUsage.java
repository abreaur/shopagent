package ro.theredpoint.shopagent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Radu DELIU
 */
@Entity
@Table(name = "ORDER_ITEM_STOCK_USAGES")
public class OrderItemStockUsage {
	
	private long id;
	private OrderItem orderItem;
	private Stock usedFrom;
	private double usedQuantity;
	
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ITEM_STOCK_USAGE_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ITEM_ID", nullable = false)
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	
	@ManyToOne
	@JoinColumn(name = "USED_FROM_ID", nullable = false)
	public Stock getUsedFrom() {
		return usedFrom;
	}
	public void setUsedFrom(Stock usedFrom) {
		this.usedFrom = usedFrom;
	}
	
	@Column(name = "USED_QUANTITY")
	public double getUsedQuantity() {
		return usedQuantity;
	}
	public void setUsedQuantity(double usedQuantity) {
		this.usedQuantity = usedQuantity;
	}
}