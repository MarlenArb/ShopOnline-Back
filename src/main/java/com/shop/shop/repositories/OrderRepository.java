package com.shop.shop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
	//public Optional<OrderEntity> findByClientName(String clientName);
	public Page<OrderEntity> findAll(Pageable pageable);
}
