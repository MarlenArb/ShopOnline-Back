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
		clientRepository.save(c);
		
		//Añadimos a la tienda el nuevo pedido
		ShopEntity s = shopRepository.findById(orderDto.getShop().getIdShop()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new ShopNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		});
		
		s.getOrders().add(order);
		shopRepository.save(s);
		
		//Añadimos a los productos los pedidos en los que aparecen
		List <ProductEntity> products = new ArrayList<ProductEntity>();
		products = order.getProducts();
		for (ProductEntity product : products) {
			productRepository.findById(product.getIdProduct()).orElseThrow(() -> {
				logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
				throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
			});
			
			product.getOrders().add(order);
			productRepository.save(product);
			
		}

		
		
		//Guardamos el pedido en la bbdd
		orderRepository.save(dte.convertOrder(orderDto));
	}

	@Override
	public void deleteOrder(Long id) {
		//Comprobamos que el id coincide con un product existente
		OrderEntity order = orderRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.ORDER_NO_CONTENT);
			throw new OrderNoContentException(DataErrorMessages.ORDER_NO_CONTENT);
		});
		
		//Borramos el pedido en el cliente
		ClientEntity c = clientRepository.findById(order.getClient().getIdClient()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CLIENT_NO_CONTENT);
			throw new ClientNoContentException(DataErrorMessages.CLIENT_NO_CONTENT);
		});
		
		c.getOrders().remove(order);
		clientRepository.save(c);
		
		//Borramos el pedido de la tienda
		ShopEntity s = shopRepository.findById(order.getShop().getIdShop()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SHOP_NO_CONTENT);
			throw new ShopNoContentException(DataErrorMessages.SHOP_NO_CONTENT);
		});
		
		s.getOrders().remove(order);
		shopRepository.save(s);
		
		//Borramos el pedido de los productos asociadosa él
		List <ProductEntity> products = new ArrayList<ProductEntity>();
		products = order.getProducts();
		for (ProductEntity product : products) {
			productRepository.findById(product.getIdProduct()).orElseThrow(() -> {
				logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
				throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
			});
			
			product.getOrders().remove(order);
			productRepository.save(product);
		}
		
		//Borramos el pedido en la bbdd
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
	public OrderDto modifyOrder(Long id, OrderDto order) {
//		   OrderEntity o = orderRepository.findById(id).orElseThrow(() -> {
//			logger.warn(DataErrorMessages.ORDER_NO_CONTENT);
//			throw new OrderNoContentException(DataErrorMessages.ORDER_NO_CONTENT);
//		});
//		
//		
//		o.setOrderDate(order.getOrderDate());
//		//Borramos los anteriores productos asociados y los volvemos a añadir (tanto por el lado del porducto como del pedido)
//		
//		//Borramos el pedido en los productos que tenia asociados
//		for(ProductEntity productEntity: o.getProducts()) {
//			productEntity.getOrders().remove(o);
//		}
//		
//		//Borramos la lista de productos que tenia el pedido anteriormente y añadimos los nuevos
//		o.getProducts().clear();
//		for (ProductDto product : order.getProducts()) {
//			ProductEntity p = dte.convertProduct(product);
//			o.getProducts().add(p);
//			p.getOrders().add(dte.convertOrder(order));
//			productRepository.save(p);
//			
//		}
//		
//		
//		//TODO: Aun no esta acabada la impl pero me parece mejor idea borrar el pedido y volver a añadirlo, posibles confictos por nuevo ID
//		orderRepository.save(o);
		deleteOrder(id);
		addOrder(order);
		return order;
			
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OrderDto> getOrdersPerPage(Integer page) {
		Page<OrderEntity> paginator = orderRepository.findAll(PageRequest.of(page, 5));
		
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
			
		}
		return matchingOrders;
	}
	
	
	

}
