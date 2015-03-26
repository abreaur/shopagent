package ro.theredpoint.shopagent.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Radu DELIU
 */
@Entity
@Table(name = "PRODUCTS")
public class Product {

	public long id;
	public String name;
	public double price;
	public String picture;
	public boolean hasStock;
	public Set<Stock> stocks;
	public Set<StockConverter> stockConverters;
	
	@Id
	@GeneratedValue
	@Column(name = "PRODUCT_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "PRICE")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Column(name = "PICTURE")
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Transient
	public boolean isHasStock() {
		return hasStock;
	}
	public void setHasStock(boolean hasStock) {
		this.hasStock = hasStock;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	public Set<Stock> getStocks() {
		return stocks;
	}
	public void setStocks(Set<Stock> stocks) {
		this.stocks = stocks;
	}
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	public Set<StockConverter> getStockConverters() {
		return stockConverters;
	}
	public void setStockConverters(Set<StockConverter> stockConverters) {
		this.stockConverters = stockConverters;
	}
}