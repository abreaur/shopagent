package ro.theredpoint.shopagent.web.model;

import java.util.HashSet;
import java.util.Set;

import ro.theredpoint.shopagent.domain.Client;
import ro.theredpoint.shopagent.domain.Role;
import ro.theredpoint.shopagent.domain.User;


/**
 * @author Radu DELIU
 */
public class UserModel {
	
	public long id;
	public long clientId;
	public String username;
	public String firstName;
	public String lastName;
	public Set<Role> roles;
	
	public UserModel(User user, Client client) {
		
		this.id = user.getId();
		if (client != null) {
			this.clientId = client.getId();
		}
		
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		
		this.roles = new HashSet<Role>(user.getRoles());
	}
	
	public UserModel(User user) {
		
		this(user, null);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}