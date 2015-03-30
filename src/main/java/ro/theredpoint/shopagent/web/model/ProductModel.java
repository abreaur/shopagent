package ro.theredpoint.shopagent.web.model;

import java.util.HashSet;
import java.util.Set;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.domain.Stock;
import ro.theredpoint.shopagent.domain.StockConverter;

/**
 * @author Radu DELIU
 */
public class ProductModel {

	private long id;
	private long stockId;
	private String name;
	private double price;
	private double quantity;
	private String picture;
	private String unitOfMeasure;
	private boolean hasStock;
	private Set<StockModel> stocks;
	
	public ProductModel(Product product) {
		this(product, false);
	}
	
	public ProductModel(Product product, boolean addStocks) {
		
		this.id = product.getId();
		this.name = product.getName();
		this.picture = product.getPicture();
		
		stocks = new HashSet<StockModel>();
		
		if (product.getStocks() != null) {
			for (Stock stock : product.getStocks()) {

				if (stock.isMain()) {
					this.price = stock.getPrice();
					this.unitOfMeasure = stock.getUnitOfMeasure().getCode();
					this.quantity = stock.getQuantity();
					this.stockId = stock.getId();
				}
				
				if ((addStocks) && (stock.getQuantity() > 0)) {
					stocks.add(new StockModel(stock));

					if (product.getStockConverters() != null) {
						for (StockConverter stockConverter : product.getStockConverters()) {
							
							if (stockConverter.getFrom().getId() == stock.getUnitOfMeasure().getId()) {
								// Add converted stock
								stocks.add(new StockModel(stock, stockConverter));
							}
						}
					}
				}
				
				if (stock.getQuantity() > 0) {
					if (addStocks) {
						this.hasStock = true;
					}
					else {
						if (stock.isMain()) {
							this.hasStock = true;
						}
					}
				}
			}
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public boolean isHasStock() {
		return hasStock;
	}
	public void setHasStock(boolean hasStock) {
		this.hasStock = hasStock;
	}

	public Set<StockModel> getStocks() {
		return stocks;
	}
	public void setStocks(Set<StockModel> stocks) {
		this.stocks = stocks;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
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
}