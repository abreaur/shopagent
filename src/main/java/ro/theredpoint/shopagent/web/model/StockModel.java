package ro.theredpoint.shopagent.web.model;

import ro.theredpoint.shopagent.domain.Stock;

/**
 * @author Radu DELIU
 */
public class StockModel {

	private long id;
	private String unitOfMeasure;
	private double quantity;
	
	public StockModel(Stock stock) {
		
		id = stock.getId();
		unitOfMeasure = stock.getUnitOfMeasure().getCode();
		quantity = stock.getQuantity();
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
}