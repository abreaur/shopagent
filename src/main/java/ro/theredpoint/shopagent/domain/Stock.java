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
@Table(name = "STOCKS")
public class Stock {
	
	private long id;
	private UnitOfMeasure unitOfMeasure;
	private Product product;
	private double quantity;
	private double price;
	
	@Id
	@GeneratedValue
	@Column(name = "STOCK_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "UNIT_OF_MEASURE_ID", nullable = false)
	public UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Column(name = "QUANTITY")
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "UNIT_PRICE")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}