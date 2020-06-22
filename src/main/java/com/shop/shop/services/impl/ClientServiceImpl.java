package com.shop.shop.services.impl;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shop.shop.dtos.ClientDto;
import com.shop.shop.services.ClientService;

public class ClientServiceImpl implements ClientService{

	@Override
	public ClientDto getClient(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addClient(ClientDto client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteClient(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClientDto> getClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDto modifyClient(Long id, ClientDto client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ClientDto> getClientsPerPage(Integer page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientDto> getClientbyName(String clientName) {
		// TODO Auto-generated method stub
		return null;
	}

}
