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
import com.shop.shop.dtos.ShopDto;
import com.shop.shop.entities.OrderEntity;
import com.shop.shop.entities.ShopEntity;
import com.shop.shop.exceptions.ClientNoContentException;
import com.shop.shop.exceptions.DataErrorMessages;
import com.shop.shop.exceptions.OrderNoContentException;
import com.shop.shop.exceptions.ShopNoContentException;
import com.shop.shop.repositories.ClientRepositoy;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.repositories.ShopRepository;
import com.shop.shop.services.ShopService;

@Service
public class ShopServiceImpl implements ShopService{
	
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
	public ShopDto getShop(Long id) {
		return etd.convertShop(shopRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new OrderNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		}));
	}

	@Override
	@Transactional
	public void addShop(ShopDto shop) {
		shopRepository.save(dte.convertShop(shop));
	}

	@Override
	public void deleteShop(Long id) {
		//Comprobamos que el id coincide con un product existente
		ShopEntity s = shopRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new ShopNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		});
		
		//Borramos los pedidos asociados a esta tienda
		for(OrderEntity o: s.getOrders()) {
			orderRepository.delete(o); //TODO: Quizas funcione mejor llamando a la funcion delete del orderServiceImpl
		}
		
		shopRepository.delete(s);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShopDto> getShops() {
		List<ShopEntity> shopsEntity = shopRepository.findAll();
		List<ShopDto> shopsDto = new ArrayList<>();
		for (ShopEntity s : shopsEntity)
			shopsDto.add(etd.convertShop(s));
		return shopsDto;
	}

	@Override
	public ShopDto modifyShop(Long id, ShopDto shop) {
		ShopEntity s = shopRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		});
		
		s.setShopName(shop.getShopName());
		s.setCIF(shop.getCIF());
		
		
		//TODO: No esta implementado la modificacion de pedidos desde la misma tienda, sino desde pedidos
		shopRepository.save(s);
		return shop;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ShopDto> getShopsPerPage(Integer page) {

		Page<ShopEntity> paginator = shopRepository.findAll(PageRequest.of(page, 5));
		
		Page<ShopDto> paginatorDto = paginator.map(new Function<ShopEntity, ShopDto>() {
			@Override
			public ShopDto apply(ShopEntity s) {
				return etd.convertShop(s);
			}
		});

		return paginatorDto;
	}

	@Override
	public List<ShopDto> getShopbyName(String shopName) {
		List<ShopEntity> allShops = shopRepository.findAll();
		List<ShopDto> matchingShops = new ArrayList<ShopDto>();
		
		for (ShopEntity s : allShops) {
			
			if (s.getShopName().toLowerCase().indexOf(shopName.toLowerCase()) != -1)
				matchingShops.add(etd.convertShop(s));
			
		}
		return matchingShops;
	}

}
