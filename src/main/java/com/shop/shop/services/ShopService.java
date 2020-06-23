package com.shop.shop.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shop.shop.dtos.ShopDto;


public interface ShopService {

	public ShopDto getShop(Long id);
	public void addShop(ShopDto shop);
	public void deleteShop(Long id);
	public List<ShopDto> getShops();
	public ShopDto modifyShop(Long id, ShopDto shop);
	public Page<ShopDto> getShopsPerPage(Integer page);
	public List<ShopDto> getShopbyName(String shopName);
}
