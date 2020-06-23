package com.shop.shop.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shop.converters.DtoToEntity;
import com.shop.shop.converters.EntityToDto;
import com.shop.shop.dtos.ClientDto;
import com.shop.shop.entities.ClientEntity;
import com.shop.shop.entities.OrderEntity;
import com.shop.shop.exceptions.ClientNoContentException;
import com.shop.shop.exceptions.DataErrorMessages;
import com.shop.shop.repositories.ClientRepositoy;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService{
	
	private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
	
	@Autowired
	EntityToDto etd;

	@Autowired
	DtoToEntity dte;
	
	@Autowired
	ClientRepositoy clientRepository;
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public ClientDto getClient(Long id) {
		return etd.convertClient(clientRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CLIENT_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.CLIENT_NO_CONTENT);
		}));
	}

	@Override
	@Transactional
	public void addClient(ClientDto clientDto) {
		clientRepository.save(dte.convertClient(clientDto));		
	}

	@Override
	@Transactional
	public void deleteClient(Long id) {
		ClientEntity c = clientRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CLIENT_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.CLIENT_NO_CONTENT);
		});

		//TODO: Puede generar error si es nulo los pedidos del cliente?
		if(c.getOrders() != null) {
			for (OrderEntity o : c.getOrders()) {
				orderRepository.delete(o);
			}
		}

		clientRepository.delete(c);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientDto> getClients() {
		List<ClientEntity> clientsEntity = clientRepository.findAll();
		List<ClientDto> clientsDto = new ArrayList<>();
		for (ClientEntity c : clientsEntity)
			clientsDto.add(etd.convertClient(c));
		return clientsDto;
	}

	@Override
	@Transactional
	public ClientDto modifyClient(Long id, ClientDto client) {
		ClientEntity c = clientRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CLIENT_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.CLIENT_NO_CONTENT);
		});
		
		c.setClientName(client.getClientName());
		c.setAge(client.getAge());
		c.setDirection(client.getDirection());
		c.setGender(client.getGender().toString());
		
		//TODO: No esta implementado la modificacion de pedidos desde clientes, sino desde pedidos
		clientRepository.save(c);
		return client;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClientDto> getClientsPerPage(Integer page) {
		Page<ClientEntity> paginator = clientRepository.findAll(PageRequest.of(page, 5));
		
		Page<ClientDto> paginatorDto = paginator.map(new Function<ClientEntity, ClientDto>() {
			@Override
			public ClientDto apply(ClientEntity c) {
				return etd.convertClient(c);
			}
		});

		return paginatorDto;
	}

	@Override
	public List<ClientDto> getClientbyName(String clientName) {
		List<ClientEntity> allClients = clientRepository.findAll();
		List<ClientDto> matchingClients = new ArrayList<>();
		
		for (ClientEntity c : allClients) {
			
			if (c.getClientName().toLowerCase().indexOf(clientName.toLowerCase()) != -1)
				matchingClients.add(etd.convertClient(c));
			
		}
		return matchingClients;
	}

}
