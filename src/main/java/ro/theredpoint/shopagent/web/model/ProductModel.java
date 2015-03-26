package ro.theredpoint.shopagent.web.model;

import java.util.HashSet;
import java.util.Set;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.domain.Stock;


/**
 * @author Radu DELIU
 */
public class ProductModel {

	public long id;
	public String name;
	public double price;
	public String picture;
	public boolean hasStock;
	public Set<StockModel> stocks;
	
	public ProductModel(Product product) {
		
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.picture = product.getPicture();
		this.hasStock = true;
		
		stocks = new HashSet<StockModel>();
		
		if (product.getStocks() != null) {
			for (Stock stock : product.getStocks()) {
				
				stocks.add(new StockModel(stock));
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
}