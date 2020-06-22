package com.shop.shop.dtos;

import java.util.ArrayList;
import java.util.List;

import com.shop.shop.enums.Enums;

public class ClientDto {

	private Long idClient;
	
	private String clientName;
	
	private String direction;
	
	private Integer age;
	
	private Enums.gender gender;
	
	private List<OrderDto> orders = new ArrayList<OrderDto>();
}
