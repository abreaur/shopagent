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
@Table(name = "PERSONS")
public class Person {
	
	public static enum ContactType {
		MAIN,
		SECONDARY
	}

	private long id;
	private String firstName;
	private String lastName;
	private String landline;
	private String cellPhone;
	private String email;
	private String description;
	private ContactType contactType;
	private Client client;
	
	@Id
	@GeneratedValue
	@Column(name = "PERSON_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "LANDLINE")
	public String getLandline() {
		return landline;
	}
	public void setLandline(String landline) {
		this.landline = landline;
	}
	
	@Column(name = "CELL_PHONE")
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhnoe) {
		this.cellPhone = cellPhnoe;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "CONTACT_TYPE")
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
