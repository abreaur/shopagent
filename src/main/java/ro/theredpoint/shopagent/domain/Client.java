package ro.theredpoint.shopagent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTS")
public class Client {

	private long id;
	private String name;
	private User user;
	private Double creditLimit;
	private Double reliability;
	private String address;
	private String CUI;
	private String fiscalCode;
	
	@Id
	@GeneratedValue
	@Column(name = "CLIENT_ID")
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
	
	@OneToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "CREDIT_LIMIT")
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	@Column(name = "RELIABILITY")
	public Double getReliability() {
		return reliability;
	}
	public void setReliability(Double reliability) {
		this.reliability = reliability;
	}
	
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "CUI")
	public String getCUI() {
		return CUI;
	}
	public void setCUI(String cUI) {
		CUI = cUI;
	}
	
	@Column(name = "FISCAL_CODE")
	public String getFiscalCode() {
		return fiscalCode;
	}
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
}
