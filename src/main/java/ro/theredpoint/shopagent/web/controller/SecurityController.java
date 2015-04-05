package ro.theredpoint.shopagent.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.Client;
import ro.theredpoint.shopagent.domain.User;
import ro.theredpoint.shopagent.service.ClientService;
import ro.theredpoint.shopagent.service.SecurityService;
import ro.theredpoint.shopagent.web.model.UserModel;

/**
 * @author Radu DELIU
 */
@RestController
public class SecurityController {

	@Autowired
	private SecurityService securityService;
	@Autowired
	private ClientService clientService; 
	
	@RequestMapping(value = "security/currentUser", produces = "application/json")
	public UserModel getCurrentUser() {
		
		User user = securityService.getCurrentUser();
		Client client = null;
		
		if (user == null) {
			return null;
		}
		
		if (securityService.isClient()) {
			
			// Identify the client
			
			for (Client currentClient : clientService.getAllClients()) {
				
				if ((currentClient.getUser() != null) || (currentClient.getUser().getId() == user.getId())) {
					client = currentClient;
					break;
				}
			}
		}
				
		return new UserModel(user, client);
	}
}