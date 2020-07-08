package com.shop.shop.dtos;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class ShopDto {

	private Long idShop;

	private String shopName;
	
	private String CIF;
	
	private String color;
	
	private List<OrderDto> orders = new ArrayList<OrderDto>();
}
