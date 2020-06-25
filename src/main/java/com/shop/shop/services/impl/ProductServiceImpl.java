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

import com.shop.shop.converters.DtoToEntity;
import com.shop.shop.converters.EntityToDto;
import com.shop.shop.dtos.OrderDto;
import com.shop.shop.dtos.ProductDto;
import com.shop.shop.entities.OrderEntity;
import com.shop.shop.entities.ProductEntity;
import com.shop.shop.entities.ShopEntity;
import com.shop.shop.entities.SupplierEntity;
import com.shop.shop.exceptions.DataErrorMessages;
import com.shop.shop.exceptions.OrderNoContentException;
import com.shop.shop.exceptions.ProductNoContentException;
import com.shop.shop.exceptions.ShopNoContentException;
import com.shop.shop.exceptions.SupplierNoContentException;
import com.shop.shop.repositories.ClientRepositoy;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.ProductRepository;
import com.shop.shop.repositories.ShopRepository;
import com.shop.shop.repositories.SupplierRepository;
import com.shop.shop.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
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

	@Autowired
	SupplierRepository supplierRepository;
	
	@Override
	public ProductDto getProduct(Long id) {
		return etd.convertProduct(productRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
			throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
		}));
	}

	@Override
	public void addProduct(ProductDto productDto) {
		ProductEntity p = dte.convertProduct(productDto);
				
		SupplierEntity s = supplierRepository.findById(productDto.getSupplier().getIdSupplier()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		s.getProducts().add(p);
		
		List <OrderEntity> orders = new ArrayList<OrderEntity>();
		for (OrderDto order : productDto.getOrders()) {
			OrderEntity o = orderRepository.findById(order.getIdOrder()).orElseThrow(() -> {
				logger.warn(DataErrorMessages.ORDER_NO_CONTENT);
				throw new OrderNoContentException(DataErrorMessages.ORDER_NO_CONTENT);
			});
			orders.add(o);
			o.getProducts().add(p);
		}
		p.setOrders(orders);
		
		
		productRepository.save(p);
		
	}

	@Override
	public void deleteProduct(Long id) {
		ProductEntity p = productRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
			throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
		});
		
		
		SupplierEntity oldSupplier = supplierRepository.findById(p.getSupplier().getIdSupplier()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		oldSupplier.getProducts().remove(p);
		

		
		//Remove Old product in orders
		for (OrderEntity o : p.getOrders()) {
			o.getProducts().remove(p);
		}
		p.getOrders().clear();
		

		
		
		//productRepository.save(p);
		
		productRepository.delete(p);
		
	}

	@Override
	public List<ProductDto> getProducts() {
		List<ProductEntity> productsEntity = productRepository.findAll();
		List<ProductDto> productsDto = new ArrayList<>();
		for (ProductEntity p : productsEntity)
			productsDto.add(etd.convertProduct(p));
		return productsDto;
	}

	@Override
	public ProductDto modifyProduct(Long id, ProductDto productDto) {
		ProductEntity p = productRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
			throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
		});
		
		p.setProductName(productDto.getProductName());
		p.setDescription(productDto.getDescription());
		p.setPrice(productDto.getPrice());
		
		SupplierEntity oldSupplier = supplierRepository.findById(p.getSupplier().getIdSupplier()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		oldSupplier.getProducts().remove(p);
		
		SupplierEntity newSupplier = supplierRepository.findById(productDto.getSupplier().getIdSupplier()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		newSupplier.getProducts().add(p);
		
		//Remove Old product in orders
		for (OrderEntity o : p.getOrders()) {
			o.getProducts().remove(p);
		}
		p.getOrders().clear();
		
		//Ad new products in orders
		List <OrderEntity> orders = new ArrayList<OrderEntity>();
		for (OrderDto order : productDto.getOrders()) {
			OrderEntity o = orderRepository.findById(order.getIdOrder()).orElseThrow(() -> {
				logger.warn(DataErrorMessages.ORDER_NO_CONTENT);
				throw new OrderNoContentException(DataErrorMessages.ORDER_NO_CONTENT);
			});
			orders.add(o);
			o.getProducts().add(p);
		}
		p.setOrders(orders);
		
		
		productRepository.save(p);
		return productDto;
	}

	@Override
	public Page<ProductDto> getProductsPerPage(Integer page) {		
	Page<ProductEntity> paginator = productRepository.findAll(PageRequest.of(page, 10));
	Page<ProductDto> paginatorDto = paginator.map(new Function<ProductEntity, ProductDto>() {
		@Override
		public ProductDto apply(ProductEntity p) {
			return etd.convertProduct(p);
		}
	});
	return paginatorDto;
	}

	@Override
	public List<ProductDto> getProductbyName(String productName) {
		List<ProductEntity> allProducts = productRepository.findAll();
		List<ProductDto> matchingProducts = new ArrayList<ProductDto>();
		
		for (ProductEntity p : allProducts) {
			if (p.getProductName().toLowerCase().indexOf(productName.toLowerCase()) != -1)
				matchingProducts.add(etd.convertProduct(p));
		}
		return matchingProducts;
	}

}
