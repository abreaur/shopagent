package ro.theredpoint.shopagent.web.model;

import java.math.BigDecimal;

import ro.theredpoint.shopagent.domain.Stock;
import ro.theredpoint.shopagent.domain.StockConverter;

/**
 * @author Radu DELIU
 */
public class StockModel {

	private long id;
	private String unitOfMeasure;
	private double quantity;
	private double price;
	
	public StockModel(Stock stock) {
		
		id = stock.getId();
		unitOfMeasure = stock.getUnitOfMeasure().getCode();
		quantity = stock.getQuantity();
		price = stock.getPrice();
	}
	
	public StockModel(Stock stock, StockConverter stockConverter) {

		id = stock.getId();
		unitOfMeasure = stockConverter.getTo().getCode();
		quantity = BigDecimal.valueOf(stock.getQuantity()).multiply(BigDecimal.valueOf(stockConverter.getRate())).doubleValue();
		price = stockConverter.getUnitPrice();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
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
}