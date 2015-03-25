package ro.theredpoint.shopagent.service;

import java.util.Set;

import ro.theredpoint.shopagent.domain.Client;

/**
 * @author Radu DELIU
 */
public interface ClientService {

	public Set<Client> getAllClients();
	public Client getClient(long clientId);
}