package com.shop.shop.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.entities.ProductEntity;


public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	public Optional<ProductEntity> findByProductName(String productName);
	public Page<ProductEntity> findAll(Pageable pageable);
}
