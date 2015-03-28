package ro.theredpoint.shopagent.web.model;

import ro.theredpoint.shopagent.domain.OrderItem;


/**
 * @author Radu DELIU
 */
public class OrderItemModel implements Comparable<OrderItemModel> {

	private long id;
	private double quantity;
	private double price;
	private ProductModel product;
	private long stockId;
	private String unitOfMeasure;
	private double amount;
	private double discount;
	
	public OrderItemModel(OrderItem orderItem) {
		
		this.id = orderItem.getId();
		this.quantity = orderItem.getQuantity();
		this.price = orderItem.getPrice();
		this.product = new ProductModel(orderItem.getProduct());
		this.stockId = orderItem.getStock().getId();
		this.unitOfMeasure = orderItem.getUnitOfMeasure().getCode();
		this.amount = orderItem.getAmount();
		this.discount = orderItem.getDiscount();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	public long getStockId() {
		return stockId;
	}
	public void setStockId(long stockId) {
		this.stockId = stockId;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Override
	public int compareTo(OrderItemModel another) {
		return (int) (id - another.getId());
	}
}