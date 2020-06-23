package com.shop.shop.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shop.shop.dtos.SupplierDto;

public interface SupplierService {

	public SupplierDto getSupplier(Long id);
	public void addSupplier(SupplierDto supplier);
	public void deleteSupplier(Long id);
	public List<SupplierDto> getSuppliers();
	public SupplierDto modifySupplier(Long id, SupplierDto supplier);
	public Page<SupplierDto> getSuppliersPerPage(Integer page);
	public List<SupplierDto> getSupplierbyName(String supplierName);
}
