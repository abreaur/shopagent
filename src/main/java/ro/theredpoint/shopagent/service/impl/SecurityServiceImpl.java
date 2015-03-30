package ro.theredpoint.shopagent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.User;
import ro.theredpoint.shopagent.repository.UserRepository;
import ro.theredpoint.shopagent.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	private static final String CLIENT_ROLE = "CLIENT";
	private static final String AGENT_ROLE = "AGENT";
	
	@Autowired
	private UserRepository userRepository;

	private UserDetails getCurrentUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	private boolean hasRole(String role) {
		
		return getCurrentUserDetails().getAuthorities().contains(new SimpleGrantedAuthority(role));
	}
	
	@Override
	public boolean isAgent() {
		return hasRole(AGENT_ROLE);
	}

	@Override
	public boolean isClient() {
		return hasRole(CLIENT_ROLE);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User getCurrentUser() {
		return userRepository.findByUsername(getCurrentUserDetails().getUsername());
	}
}