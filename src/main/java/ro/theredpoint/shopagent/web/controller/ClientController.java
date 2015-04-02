package ro.theredpoint.shopagent.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.theredpoint.shopagent.domain.Client;
import ro.theredpoint.shopagent.domain.Person;
import ro.theredpoint.shopagent.service.ClientService;

/**
 * @author Radu DELIU
 */
@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	private List<Client> prepareResponse(List<Client> clients) {
		
		for (Client client : clients) {
			for (Person person : client.getContacts()) {
				person.setClient(null);
			}
		}
		
		return clients;
	}
	
	
	@RequestMapping(value = "clients", produces = "application/json")
	public List<Client> getClients() {
		return prepareResponse(clientService.getAllClients());
	}
	
	@RequestMapping(value = "clients/{name}", produces = "application/json")
	public List<Client> getClients(@PathVariable String name) {
		return prepareResponse(clientService.getClients(name));
	}
}