package com.shop.shop.converters;

import java.util.List;


import com.shop.shop.dtos.ClientDto;
import com.shop.shop.dtos.OrderDto;
import com.shop.shop.dtos.ProductDto;
import com.shop.shop.dtos.ShopDto;
import com.shop.shop.dtos.SupplierDto;
import com.shop.shop.entities.ClientEntity;
import com.shop.shop.entities.OrderEntity;
import com.shop.shop.entities.ProductEntity;
import com.shop.shop.entities.ShopEntity;
import com.shop.shop.entities.SupplierEntity;

public interface DtoToEntity {
	
	public ClientEntity convertClient(ClientDto clientDto);
	public List<OrderEntity> convertOrders(List<OrderDto> ordersDto);
	public OrderEntity convertOrder(OrderDto orderDto);
	public ShopEntity convertShop(ShopDto shopDto);
	public List<ProductEntity> convertProducts(List<ProductDto> productsDto);
	public ProductEntity convertProduct(ProductDto productDto);
	public SupplierEntity convertSupplier(SupplierDto supplierDto);

}
