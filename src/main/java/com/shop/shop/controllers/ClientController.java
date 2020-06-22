package com.shop.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shop.dtos.ClientDto;
import com.shop.shop.services.ClientService;


@RestController
@RequestMapping("/client")
public class ClientController {
	

	@Autowired
	private ClientService clientService;
	
	//GET
	@GetMapping("/{id}")
	public ClientDto getClient(@Validated @PathVariable Long id) {
		return clientService.getClient(id);
	}
	

	//POST
	@PostMapping
	public void addClient(@Validated @RequestBody ClientDto client) {
		clientService.addClient(client);
	};

	//DELETE
	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable Long id) {
		clientService.deleteClient(id);
	}

	//GET
	@GetMapping
	public List<ClientDto> getClients() {
		return clientService.getClients();
	}

	//PUT
	@PutMapping("/{id}")
	public ClientDto modifyClient(@Validated @PathVariable Long id, @Validated @RequestBody ClientDto client) {
		return clientService.modifyClient(id, client);
	}
	
	//GET
	@GetMapping("/page/{page}")
	public Page<ClientDto> getClientsPerPage(@PathVariable Integer page) {
		return clientService.getClientsPerPage(page);
	}

	//GET
	@GetMapping("/page/*/{clientName}")
	public List<ClientDto> getClientbyName(@PathVariable String clientName) {
		return clientService.getClientbyName(clientName);
	}

}
