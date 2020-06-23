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

public interface EntityToDto {
	
	public ClientDto convertClient(ClientEntity clientEntity);
	public List<OrderDto> convertOrders(List<OrderEntity> ordersEntity);
	public OrderDto convertOrder(OrderEntity orderEntity);
	public ShopDto convertShop(ShopEntity shopEntity);
	public List<ProductDto> convertProducts(List<ProductEntity> productsEntity);
	public ProductDto convertProduct(ProductEntity productEntity);
	public SupplierDto convertSupplier(SupplierEntity supplierEntity);

}
