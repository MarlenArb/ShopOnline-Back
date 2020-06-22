package com.shop.shop.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderDto {

	private Long idOrder;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String orderDate;
	
	private List<ProductDto> products = new ArrayList<ProductDto>();
	private List<ClientDto> clients = new ArrayList<ClientDto>();
	
}
