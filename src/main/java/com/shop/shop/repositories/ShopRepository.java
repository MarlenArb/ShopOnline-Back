package com.shop.shop.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.entities.ShopEntity;


public interface ShopRepository extends JpaRepository<ShopEntity, Long>{
	public Optional<ShopEntity> findByShopName(String shopName);
	public Page<ShopEntity> findAll(Pageable pageable);
}
