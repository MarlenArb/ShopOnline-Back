package com.shop.shop.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.entities.ClientEntity;


public interface ClientRepositoy extends JpaRepository<ClientEntity, Long>{
	public Optional<ClientEntity> findByClientName(String clientName);
	public Page<ClientEntity> findAll(Pageable pageable);
}
