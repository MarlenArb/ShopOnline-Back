package com.shop.shop.dtos;


import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProductDto {
	
	private Long idProduct;
	
	private Double price;
	
	private String productName;
	
	private String description;

	private SupplierDto supplier;
	
	private List<OrderDto> orders = new ArrayList<OrderDto>();

}
