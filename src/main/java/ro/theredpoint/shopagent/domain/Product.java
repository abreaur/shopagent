package ro.theredpoint.shopagent.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Radu DELIU
 */
@Entity
@Table(name = "PRODUCTS")
public class Product {

	public long id;
	public String name;
	public String desciption;
	public String picture;
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
	
	@Column(name = "DESCRIPTION")
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	
	@Column(name = "PICTURE")
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
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