package ro.theredpoint.shopagent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Client;
import ro.theredpoint.shopagent.repository.ClientRepository;
import ro.theredpoint.shopagent.service.ClientService;

/**
 * @author Radu DELIU
 */
@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository; 
	
	@Override
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	@Override
	public Client getClient(long clientId) {
		return clientRepository.findOne(clientId);
	}

	@Override
	public List<Client> getClients(String name) {
		return clientRepository.findByName(name);
	}
}