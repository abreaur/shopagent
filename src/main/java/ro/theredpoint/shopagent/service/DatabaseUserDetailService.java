package ro.theredpoint.shopagent.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Role;

@Service
public class DatabaseUserDetailService implements UserDetailsService {
	
	@Autowired
	private SecurityService securityService; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ro.theredpoint.shopagent.domain.User user = securityService.findByUsername(username);
		
		if (user == null) {
			return null;
		}
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
}