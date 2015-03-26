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
@Table(name = "STOCK_CONVERTERS")
public class StockConverter {
	
	private long id;
	private Product product;
	private UnitOfMeasure from;
	private UnitOfMeasure to;
	private double rate;
	private double unitPrice;
	
	@Id
	@GeneratedValue
	@Column(name = "STOCK_CONVERTER_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	@JoinColumn(name = "FROM_UNIT_OF_MEASURE_ID", nullable = false)
	public UnitOfMeasure getFrom() {
		return from;
	}
	public void setFrom(UnitOfMeasure from) {
		this.from = from;
	}

	@ManyToOne
	@JoinColumn(name = "TO_UNIT_OF_MEASURE_ID", nullable = false)
	public UnitOfMeasure getTo() {
		return to;
	}
	public void setTo(UnitOfMeasure to) {
		this.to = to;
	}
	
	@Column(name = "RATE")
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	@Column(name = "UNIT_PRICE")
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
}