package com.shop.shop.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SupplierDto {
	
	private Long idSupplier;
	
	private String name;
	
	private List<ProductDto> products = new ArrayList<ProductDto>();
	
	private Integer numberProducts;
}
