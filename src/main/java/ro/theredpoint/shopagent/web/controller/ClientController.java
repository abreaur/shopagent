package ro.theredpoint.shopagent.web.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.Client;
import ro.theredpoint.shopagent.service.ClientService;

/**
 * @author Radu DELIU
 */
@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "clients", produces = "application/json")
	public Set<Client> getProducts() {
		return clientService.getAllClients();
	}
}