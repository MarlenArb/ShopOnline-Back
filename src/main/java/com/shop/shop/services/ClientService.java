package com.shop.shop.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shop.shop.dtos.ClientDto;

@Service
public interface ClientService {

	public ClientDto getClient(Long id);
	public void addClient(ClientDto client);
	public void deleteClient(Long id);
	public List<ClientDto> getClients();
	public ClientDto modifyClient(Long id, ClientDto client);
	public Page<ClientDto> getClientsPerPage(Integer page);
	public List<ClientDto> getClientbyName(String clientName);
	
}
