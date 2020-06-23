package com.shop.shop.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shop.shop.converters.DtoToEntity;
import com.shop.shop.converters.EntityToDto;
import com.shop.shop.dtos.SupplierDto;
import com.shop.shop.repositories.ClientRepositoy;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.repositories.ShopRepository;
import com.shop.shop.services.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService{

	private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
	
	@Autowired
	EntityToDto etd;

	@Autowired
	DtoToEntity dte;
	
	@Autowired
	ClientRepositoy clientRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public SupplierDto getSupplier(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSupplier(SupplierDto supplier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSupplier(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SupplierDto> getSuppliers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SupplierDto modifySupplier(Long id, SupplierDto supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SupplierDto> getSuppliersPerPage(Integer page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplierDto> getSupplierbyName(String supplierName) {
		// TODO Auto-generated method stub
		return null;
	}

}
