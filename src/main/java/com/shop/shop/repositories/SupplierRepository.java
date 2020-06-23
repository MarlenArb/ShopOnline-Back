package com.shop.shop.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.entities.SupplierEntity;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long>{
	public Optional<SupplierEntity> findByName(String name);
	public Page<SupplierEntity> findAll(Pageable pageable);
}
