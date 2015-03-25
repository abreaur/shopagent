package ro.theredpoint.shopagent.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.User;
import ro.theredpoint.shopagent.service.SecurityService;

/**
 * @author Radu DELIU
 */
@RestController
public class SecurityController {

	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "security/currentUser", produces = "application/json")
	public User getCurrentUser() {
		
		User user = securityService.getCurrentUser();
		
		if (user == null) {
			return null;
		}
		
		user.setPassword("ENCRYPTED");
				
		return user;
	}
}