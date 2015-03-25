package ro.theredpoint.shopagent.service;

import ro.theredpoint.shopagent.domain.User;

/**
 * @author Radu DELIU
 */
public interface SecurityService {

	public boolean isAgent();
	public boolean isClient();
	
	public User findByUsername(String username);
	public User getCurrentUser();
}