package com.shop.shop.converters.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.shop.converters.EntityToDto;
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
import com.shop.shop.enums.Enums;

@Service
public class EntityToDtoImpl implements EntityToDto{
	
	@Override
	public ClientDto convertClient(ClientEntity clientEntity) {
		ClientDto clientDto = new ClientDto();
		clientDto.setIdClient(clientEntity.getIdClient());
		clientDto.setClientName(clientEntity.getClientName());
		clientDto.setAge(clientEntity.getAge());
		clientDto.setDirection(clientEntity.getDirection());
		clientDto.setGender(Enums.gender.valueOf(clientEntity.getGender()));
		//clientDto.setOrders(convertOrders(clientEntity.getOrders()));
		return clientDto;
	}
	
	
	@Override
	public List<OrderDto> convertOrders(List<OrderEntity> ordersEntity){
		List<OrderDto> ordersDto = new ArrayList<OrderDto>();
		for (OrderEntity orderEntity : ordersEntity) {
			ordersDto.add(convertOrder(orderEntity));
		}
		return ordersDto;
	}
	
	@Override
	public OrderDto convertOrder(OrderEntity orderEntity) {
		OrderDto orderDto = new OrderDto();
		orderDto.setClient(convertClient(orderEntity.getClient()));
		orderDto.setIdOrder(orderEntity.getIdOrder());
		orderDto.setOrderDate(orderEntity.getOrderDate());
		orderDto.setProducts(convertProducts(orderEntity.getProducts())); //Bucle
		orderDto.setShop(convertShop(orderEntity.getShop()));
		return orderDto;
	}
	
	@Override
	public ShopDto convertShop(ShopEntity shopEntity) {
		ShopDto shopDto = new ShopDto();
		shopDto.setIdShop(shopEntity.getIdShop());
		shopDto.setShopName(shopEntity.getShopName());
		//shopDto.setOrders(convertOrders(shopEntity.getOrders()));
		shopDto.setCIF(shopEntity.getCIF());
		return shopDto;
	}
	
	@Override
	public List<ProductDto> convertProducts(List<ProductEntity> productsEntity){
		List<ProductDto> productsDto = new ArrayList<ProductDto>();
		for (ProductEntity productEntity : productsEntity) {
			productsDto.add(convertProduct(productEntity));
		}
		return productsDto;
	}
	
	@Override
	public ProductDto convertProduct(ProductEntity productEntity) {
		ProductDto productDto = new ProductDto();
		productDto.setIdProduct(productEntity.getIdProduct());
		productDto.setProductName(productEntity.getProductName());
		productDto.setDescription(productEntity.getDescription());
		productDto.setPrice(productEntity.getPrice());
		productDto.setSupplier(convertSupplier(productEntity.getSupplier())); //TODO: Error
		productDto.setOrders(convertOrders(productEntity.getOrders()));
		return productDto;
	}
	
	@Override
	public SupplierDto convertSupplier(SupplierEntity supplierEntity) {
		SupplierDto supplierDto = new SupplierDto();
		supplierDto.setIdSupplier(supplierEntity.getIdSupplier());
		supplierDto.setName(supplierEntity.getName());
		supplierDto.setNumberProducts(supplierEntity.getProducts().size());
		//supplierDto.setProducts(convertProducts(supplierEntity.getProducts()));
		return supplierDto;
	}

}
