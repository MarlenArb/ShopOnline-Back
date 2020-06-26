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
import com.shop.shop.dtos.OrderDto;
import com.shop.shop.dtos.ProductDto;
import com.shop.shop.entities.ClientEntity;
import com.shop.shop.entities.OrderEntity;
import com.shop.shop.entities.ProductEntity;
import com.shop.shop.entities.ShopEntity;
import com.shop.shop.exceptions.ClientNoContentException;
import com.shop.shop.exceptions.DataErrorMessages;
import com.shop.shop.exceptions.OrderNoContentException;
import com.shop.shop.exceptions.ProductNoContentException;
import com.shop.shop.exceptions.ShopNoContentException;
import com.shop.shop.repositories.ClientRepositoy;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.repositories.ShopRepository;
import com.shop.shop.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
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
	public OrderDto getOrder(Long id) {
		return etd.convertOrder(orderRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.ORDER_NO_CONTENT);
			throw new OrderNoContentException(DataErrorMessages.ORDER_NO_CONTENT);
		}));
	}

	@Override
	@Transactional
	public void addOrder(OrderDto orderDto) {
		OrderEntity order = dte.convertOrder(orderDto);
		
		//Añadimos al cliente el nuevo pedido
		ClientEntity c = clientRepository.findById(orderDto.getClient().getIdClient()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CLIENT_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.CLIENT_NO_CONTENT);
		});
		
		c.getOrders().add(order);
		
		//Añadimos a la tienda el nuevo pedido
		ShopEntity s = shopRepository.findById(orderDto.getShop().getIdShop()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new ShopNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		});
		
		s.getOrders().add(order);
		
		//Añadimos a los productos los pedidos en los que aparecen
		List <ProductEntity> products = new ArrayList<ProductEntity>();
		//products = order.getProducts();
		for (ProductDto product : orderDto.getProducts()) {
			ProductEntity p = productRepository.findById(product.getIdProduct()).orElseThrow(() -> {
				logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
				throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
				
			});
			
			products.add(p);
			p.getOrders().add(order);
			
		}
		order.setProducts(products);
		
		
		//Guardamos el pedido en la bbdd
		orderRepository.save(order);
	}

	@Override
	public void deleteOrder(Long id) {
		//Comprobamos que el id coincide con un product existente
		OrderEntity order = orderRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.ORDER_NO_CONTENT);
			throw new OrderNoContentException(DataErrorMessages.ORDER_NO_CONTENT);
		});		

				for (ProductEntity product : order.getProducts()) {
					product.getOrders().remove(order);
			}
		
		orderRepository.delete(order);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderDto> getOrders() {
		List<OrderEntity> ordersEntity = orderRepository.findAll();
		List<OrderDto> ordersDto = new ArrayList<>();
		for (OrderEntity o : ordersEntity)
			ordersDto.add(etd.convertOrder(o));
		return ordersDto;
	}

	@Override
	public OrderDto modifyOrder(Long id, OrderDto orderDto) { //Cuidado le cambie el nombre al orderDto antes order
		//-------------------------------------------Buscamos la entidad del pedido para poder trabajar----------------------------------------
		OrderEntity order = orderRepository.findById(orderDto.getIdOrder()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.ORDER_NO_CONTENT);
			throw new OrderNoContentException(DataErrorMessages.ORDER_NO_CONTENT);
		});
		
		//-----------------------------------------Primero Seteamos la entidad con los nuevos valores-------------------------------------------
		order.setOrderDate(orderDto.getOrderDate());
		
		//----------------------------------------------Cliente Asociado-------------------------------------------------------------------------
		//1. Buscamos la ENTIDAD cliente asociada anterior
		ClientEntity oldClient = clientRepository.findById(order.getClient().getIdClient()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CLIENT_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.CLIENT_NO_CONTENT);
		});
		
		ClientEntity newClient = clientRepository.findById(orderDto.getClient().getIdClient()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CLIENT_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.CLIENT_NO_CONTENT);
		});
		
		//2.Borramos el pedido de la entidad cliente
		oldClient.getOrders().remove(order);
		//3.Añadimos el nuevo pedido al cliente y viceversa
		newClient.getOrders().add(order);
		order.setClient(newClient);
		
		//----------------------------------------------Tienda Asociada-------------------------------------------------------------------------
		//1. Buscamos la ENTIDAD tienda asociada anterior
		ShopEntity oldShop = shopRepository.findById(order.getShop().getIdShop()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new ShopNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		});
		
		ShopEntity newShop = shopRepository.findById(orderDto.getShop().getIdShop()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new ShopNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		});
		
		//2.Borramos el pedido de la entidad tienda anterior
		oldShop.getOrders().remove(order);
		//3.Añadimos el nuevo pedido a la nueva tienda asignada y viceversa
		newShop.getOrders().add(order);
		order.setShop(newShop);
		
		//----------------------------------------------Productos Asociados-------------------------------------------------------------------------
		//1. Borramos las ENTIDADES producto asociadas anteriormente
		for (ProductEntity p : order.getProducts()) {
			p.getOrders().remove(order);
		}
		order.getProducts().clear();
		
		//2.Añadimos las nuevas
		List <ProductEntity> newProducts = new ArrayList<ProductEntity>();
		for (ProductDto product : orderDto.getProducts()) {
			ProductEntity p = productRepository.findById(product.getIdProduct()).orElseThrow(() -> {
				logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
				throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
			});
		
			newProducts.add(p);
			p.getOrders().add(order);
			
		}
		order.setProducts(newProducts);
		
		
		//Guardamos el pedido en la bbdd
		orderRepository.save(order);
		return orderDto;
			
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OrderDto> getOrdersPerPage(Integer page) {
		Page<OrderEntity> paginator = orderRepository.findAll(PageRequest.of(page, 10));
		
		Page<OrderDto> paginatorDto = paginator.map(new Function<OrderEntity, OrderDto>() {
			@Override
			public OrderDto apply(OrderEntity o) {
				return etd.convertOrder(o);
			}
		});

		return paginatorDto;
	}

	@Override
	public List<OrderDto> getOrdertbyRef(String ref) {
		List<OrderEntity> allOrders = orderRepository.findAll();
		List<OrderDto> matchingOrders = new ArrayList<OrderDto>();
		
		for (OrderEntity o : allOrders) {
			
			if (o.getOrderDate().toLowerCase().indexOf(ref.toLowerCase()) != -1)
				matchingOrders.add(etd.convertOrder(o));
			
			if (o.getShop().getShopName().toLowerCase().indexOf(ref.toLowerCase()) != -1)
				matchingOrders.add(etd.convertOrder(o));
			
			if (o.getClient().getClientName().toLowerCase().indexOf(ref.toLowerCase()) != -1)
				matchingOrders.add(etd.convertOrder(o));
			
		}
		return matchingOrders;
	}
	
	
	

}
