package ro.theredpoint.shopagent.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	private static final String CLIENT_ROLE = "ROLE_CLIENT";
	private static final String AGENT_ROLE = "ROLE_AGENT";
	
	private UserDetails getCurrentUser() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	private boolean hasRole(String role) {
		
		return getCurrentUser().getAuthorities().contains(new SimpleGrantedAuthority(role));
	}
	
	@Override
	public boolean isAgent() {
		return hasRole(AGENT_ROLE);
	}

	@Override
	public boolean isClient() {
		return hasRole(CLIENT_ROLE);
	}
}
