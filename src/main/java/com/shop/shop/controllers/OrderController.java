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

import com.shop.shop.dtos.OrderDto;
import com.shop.shop.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	//GET
	@GetMapping("/{id}")
	public OrderDto getOrder(@Validated @PathVariable Long id) {
		return orderService.getOrder(id);
	}
	

	//POST
	@PostMapping
	public void addOrder(@Validated @RequestBody OrderDto orderDto) {
		orderService.addOrder(orderDto);
	};

	//DELETE
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
	}

	//GET
	@GetMapping
	public List<OrderDto> getOrders() {
		return orderService.getOrders();
	}

	//PUT
	@PutMapping("/{id}")
	public OrderDto modifyOrder(@Validated @PathVariable Long id, @Validated @RequestBody OrderDto order) {
		return orderService.modifyOrder(id, order);
	}
	
	//GET
	@GetMapping("/page/{page}")
	public Page<OrderDto> getOrdersPerPage(@PathVariable Integer page) {
		return orderService.getOrdersPerPage(page);
	}

	//GET
	@GetMapping("/page/*/{orderName}")
	public List<OrderDto> getOrderbyRef(@PathVariable String ref) {
		return orderService.getOrdertbyRef(ref);
	}

}
