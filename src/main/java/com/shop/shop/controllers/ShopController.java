package com.shop.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shop.dtos.ShopDto;
import com.shop.shop.services.ShopService;

@RestController
@RequestMapping("/shop")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,  RequestMethod.DELETE, RequestMethod.PUT})
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	//GET
	@GetMapping("/{id}")
	public ShopDto getShop(@Validated @PathVariable Long id) {
		return shopService.getShop(id);
	}
	

	//POST
	@PostMapping
	public void addShop(@Validated @RequestBody ShopDto shop) {
		shopService.addShop(shop);
	};

	//DELETE
	@DeleteMapping("/{id}")
	public void deleteShop(@PathVariable Long id) {
		shopService.deleteShop(id);
	}

	//GET
	@GetMapping
	public List<ShopDto> getShops() {
		return shopService.getShops();
	}

	//PUT
	@PutMapping("/{id}")
	public ShopDto modifyShop(@Validated @PathVariable Long id, @Validated @RequestBody ShopDto shop) {
		return shopService.modifyShop(id, shop);
	}
	
	//GET
	@GetMapping("/page/{page}")
	public Page<ShopDto> getShopsPerPage(@PathVariable Integer page) {
		return shopService.getShopsPerPage(page);
	}

	//GET
	@GetMapping("/page/*/{shopName}")
	public List<ShopDto> getClientbyName(@PathVariable String shopName) {
		return shopService.getShopbyName(shopName);
	}

}
