package ro.theredpoint.shopagent.service;

import java.util.List;

import ro.theredpoint.shopagent.domain.Client;

/**
 * @author Radu DELIU
 */
public interface ClientService {

	public List<Client> getAllClients();
	public Client getClient(long clientId);
	public List<Client> getClients(String name);
}