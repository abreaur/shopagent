package ro.theredpoint.shopagent.domain;

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
@Table(name = "ORDER_ITEMS")
public class OrderItem {

	private long id;
	private double quantity;
	private double price;
	private Order order;
	private Product product;
	private Stock stock;
	private UnitOfMeasure unitOfMeasure;
	private double amount;
	private double discount;
	private Set<OrderItemStockUsage> stockUsages;
	
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ITEM")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "QUANTITY")
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "PRICE")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false)
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Column(name = "AMOUNT")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Column(name = "DISCOUNT")
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	@ManyToOne
	@JoinColumn(name = "STOCK_ID", nullable = false)
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	@ManyToOne
	@JoinColumn(name = "UNIT_OF_MEASURE_ID", nullable = false)
	public UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	
	@OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
	public Set<OrderItemStockUsage> getStockUsages() {
		return stockUsages;
	}
	public void setStockUsages(Set<OrderItemStockUsage> stockUsages) {
		this.stockUsages = stockUsages;
	}
}