package com.shop.shop.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shop.converters.DtoToEntity;
import com.shop.shop.converters.EntityToDto;
import com.shop.shop.dtos.SupplierDto;
import com.shop.shop.entities.ProductEntity;
import com.shop.shop.entities.SupplierEntity;
import com.shop.shop.exceptions.DataErrorMessages;
import com.shop.shop.exceptions.SupplierNoContentException;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.repositories.ShopRepository;
import com.shop.shop.repositories.SupplierRepository;
import com.shop.shop.services.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService{

	private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
	
	@Autowired
	EntityToDto etd;

	@Autowired
	DtoToEntity dte;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public SupplierDto getSupplier(Long id) {
		return etd.convertSupplier(supplierRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		}));
	}

	@Override
	@Transactional
	public void addSupplier(SupplierDto supplier) {
		supplierRepository.save(dte.convertSupplier(supplier));
		
	}

	@Override
	public void deleteSupplier(Long id) {
		//Comprobamos que el id coincide con un proveedor existente
		SupplierEntity s = supplierRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		//Borramos los productos asociados a este distribuidor
		for(ProductEntity p: s.getProducts()) {
			productRepository.delete(p); //TODO: Quizas funcione mejor llamando a la funcion delete del orderServiceImpl
		}
		supplierRepository.delete(s);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SupplierDto> getSuppliers() {
		List<SupplierEntity> suppliersEntity = supplierRepository.findAll();
		List<SupplierDto> suppliersDto = new ArrayList<>();
		for (SupplierEntity s : suppliersEntity)
			suppliersDto.add(etd.convertSupplier(s));
		return suppliersDto;
	}

	@Override
	public SupplierDto modifySupplier(Long id, SupplierDto supplier) {
		SupplierEntity s = supplierRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		s.setName(supplier.getName());
		
		//Actualizamos los datos del proveedor en los productos que suministra
		for (ProductEntity p : s.getProducts()) {
			p.setSupplier(s);
			productRepository.save(p);
		}

		supplierRepository.save(s);
		return supplier;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SupplierDto> getSuppliersPerPage(Integer page) {
		Page<SupplierEntity> paginator = supplierRepository.findAll(PageRequest.of(page, 10));
		Page<SupplierDto> paginatorDto = paginator.map(new Function<SupplierEntity, SupplierDto>() {
			@Override
			public SupplierDto apply(SupplierEntity s) {
				return etd.convertSupplier(s);
			}
		});
		return paginatorDto;
	}

	@Override
	public List<SupplierDto> getSupplierbyName(String supplierName) {
		List<SupplierEntity> allSuppliers = supplierRepository.findAll();
		List<SupplierDto> matchingSuppliers= new ArrayList<SupplierDto>();
		
		for (SupplierEntity s : allSuppliers) {
			
			if (s.getName().toLowerCase().indexOf(supplierName.toLowerCase()) != -1)
				matchingSuppliers.add(etd.convertSupplier(s));
			
		}
		return matchingSuppliers;
	}

}
