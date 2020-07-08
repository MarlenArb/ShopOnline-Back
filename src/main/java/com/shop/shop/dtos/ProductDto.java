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
	
	private boolean isInOrder = false;

	private Integer numberOrders;
	
	private String color;
	
	private SupplierDto supplier = new SupplierDto();
	
	private List<OrderDto> orders = new ArrayList<OrderDto>();

}
