package ro.theredpoint.shopagent.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Radu DELIU
 */
@Entity
@Table(name = "ORDERS")
public class Order {

	public static enum OrderStatus {
		BASKET,
		PLACED,
		CONFIRMED,
		DELIVERED,
		CANCELLED
	}
	
	private long id;
	private User user;
	private Client client;
	private OrderStatus orderStatus;
	private Date created;
	private Date cancelDate;
	private Date expectedDeliveryDate;
	private double amount;
	private Set<OrderItem> orderItems;

	
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	@Column(name = "ORDER_STATUS")
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Column(name = "CREATED")
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "CANCEL_DATE")
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	@Column(name = "EXPECTED_DELIVERY_DATE")
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	
	@Column(name = "AMOUNT")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}