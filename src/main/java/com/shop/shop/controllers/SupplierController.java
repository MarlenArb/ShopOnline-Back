package com.shop.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shop.dtos.SupplierDto;
import com.shop.shop.services.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	//GET
	@GetMapping("/{id}")
	public SupplierDto getSupplier(@Validated @PathVariable Long id) {
		return supplierService.getSupplier(id);
	}
	

	//POST
	@PostMapping
	public void addSupplier(@Validated @RequestBody SupplierDto supplier) {
		supplierService.addSupplier(supplier);
	};

	//DELETE
	@DeleteMapping("/{id}")
	public void deleteSupplier(@PathVariable Long id) {
		supplierService.deleteSupplier(id);
	}

	//GET
	@GetMapping
	public List<SupplierDto> getShops() {
		return supplierService.getSuppliers();
	}

	//PUT
	@PutMapping("/{id}")
	public SupplierDto modifySupplier(@Validated @PathVariable Long id, @Validated @RequestBody SupplierDto supplier) {
		return supplierService.modifySupplier(id, supplier);
	}
	
	//GET
	@GetMapping("/page/{page}")
	public Page<SupplierDto> getSuppliersPerPage(@PathVariable Integer page) {
		return supplierService.getSuppliersPerPage(page);
	}

	//GET
	@GetMapping("/page/*/{supplierName}")
	public List<SupplierDto> getSupplierbyName(@PathVariable String supplierName) {
		return supplierService.getSupplierbyName(supplierName);
	}

}
