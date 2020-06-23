package com.shop.shop.converters.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.shop.converters.DtoToEntity;
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

@Service
public class DtoToEntityImpl implements DtoToEntity{

	@Override
	public ClientEntity convertClient(ClientDto clientDto) {
		ClientEntity clientEntity = new ClientEntity();
		if (clientDto.getIdClient() != null) {
			clientEntity.setIdClient(clientDto.getIdClient());
		}
		clientEntity.setClientName(clientDto.getClientName());
		clientEntity.setDirection(clientDto.getDirection());
		clientEntity.setAge(clientDto.getAge());
		clientEntity.setGender(clientDto.getGender().toString());
		clientEntity.setOrders(convertOrders(clientDto.getOrders()));
		return clientEntity;
	}
	
	
	@Override
	public List<OrderEntity> convertOrders(List<OrderDto> ordersDto){
		List<OrderEntity> ordersEntity = new ArrayList<OrderEntity>();
		for (OrderDto orderDto : ordersDto) {
			ordersEntity.add(convertOrder(orderDto));
		}
		return ordersEntity;
	}
	
	@Override
	public OrderEntity convertOrder(OrderDto orderDto) {
		OrderEntity orderEntity = new OrderEntity();
		if (orderDto.getIdOrder() != null) {
			orderEntity.setIdOrder(orderDto.getIdOrder());
		}
		orderEntity.setClient(convertClient(orderDto.getClient()));
		orderEntity.setOrderDate(orderDto.getOrderDate());
		orderEntity.setShop(convertShop(orderDto.getShop()));
		orderEntity.setProducts(convertProducts(orderDto.getProducts()));
		return orderEntity;
	}
	
	@Override
	public ShopEntity convertShop(ShopDto shopDto) {
		ShopEntity shopEntity = new ShopEntity();
		if (shopDto.getIdShop() != null) {
			shopEntity.setIdShop(shopDto.getIdShop());
		}
		shopEntity.setOrders(convertOrders(shopDto.getOrders()));
		shopEntity.setShopName(shopDto.getShopName());
		shopEntity.setCIF(shopDto.getCIF());
		return shopEntity;
	}
	
	@Override
	public List<ProductEntity> convertProducts(List<ProductDto> productsDto){
		List<ProductEntity> productsEntity = new ArrayList<ProductEntity>();
		for (ProductDto productDto : productsDto) {
			productsEntity.add(convertProduct(productDto));
		}
		return productsEntity;
	}
	
	@Override
	public ProductEntity convertProduct(ProductDto productDto) {
		ProductEntity productEntity = new ProductEntity();
		if (productDto.getIdProduct() != null) {
			productEntity.setIdProduct(productDto.getIdProduct());
		}
		productEntity.setProductName(productDto.getProductName());
		productEntity.setDescription(productDto.getDescription());
		productEntity.setPrice(productDto.getPrice());
		productEntity.setOrders(convertOrders(productDto.getOrders()));
		productEntity.setSupplier(convertSupplier(productDto.getSupplier()));
		return productEntity;
	}
	
	@Override
	public SupplierEntity convertSupplier(SupplierDto supplierDto) {
		SupplierEntity supplierEntity = new SupplierEntity();
		if (supplierDto.getIdSupplier() != null) {
			supplierEntity.setIdSupplier(supplierDto.getIdSupplier());
		}
		supplierEntity.setName(supplierDto.getName());
		supplierEntity.setProducts(convertProducts(supplierDto.getProducts()));
		

		return supplierEntity;
	}
	
	
	
	
	

}
