package com.shop.shop.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shop.shop.dtos.OrderDto;


public interface OrderService {
	
	public OrderDto getOrder(Long id);
	public void addOrder(OrderDto orderDto);
	public void deleteOrder(Long id);
	public List<OrderDto> getOrders();
	public OrderDto modifyOrder(Long id, OrderDto order);
	public Page<OrderDto> getOrdersPerPage(Integer page);
	public List<OrderDto> getOrdertbyRef(String ref);

}
