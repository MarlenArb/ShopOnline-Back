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
import com.shop.shop.dtos.ProductDto;
import com.shop.shop.entities.ProductEntity;
import com.shop.shop.entities.SupplierEntity;
import com.shop.shop.exceptions.DataErrorMessages;
import com.shop.shop.exceptions.ProductNoContentException;
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
		SupplierEntity supplier = supplierRepository.findById(productDto.getSupplier().getIdSupplier()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		//Le añado el producto al proveedor
		supplier.getProducts().add(dte.convertProduct(productDto));
		supplierRepository.save(supplier);
		//Guardo el producto en la base de datos
		productRepository.save(dte.convertProduct(productDto));
		
	}

	@Override
	public void deleteProduct(Long id) {
		ProductEntity p = productRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
			throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
		});
		
		//Borro el pedido de la lista de pedidos del proveedor
		SupplierEntity supplier = supplierRepository.findById(p.getSupplier().getIdSupplier()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		supplier.getProducts().remove(p);
		supplierRepository.save(supplier);//TODO: Revisar si es necesario guardar despues del remove product
		
		productRepository.delete(p);
		
		//TODO: NO borro los pedidos existentes porque son pedidos realizados en el momento que si estaban disponibles los productos
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
	public ProductDto modifyProduct(Long id, ProductDto product) {
		ProductEntity p = productRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.PRODUCT_NO_CONTENT);
			throw new ProductNoContentException(DataErrorMessages.PRODUCT_NO_CONTENT);
		});
		
		p.setDescription(product.getDescription());
		p.setPrice(product.getPrice());
		p.setProductName(product.getProductName());
		
		SupplierEntity s = supplierRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SUPPLIER_NO_CONTENT);
			throw new SupplierNoContentException(DataErrorMessages.SUPPLIER_NO_CONTENT);
		});
		
		s.getProducts().remove(p);
		s.getProducts().add(p);
		supplierRepository.save(s);
		
		
		p.setSupplier(dte.convertSupplier(product.getSupplier()));
		productRepository.save(p);
		
		//No modifico el nombre de los productos que se encuentren en pedidos anteriormente hechos
		return product;
	}

	@Override
	public Page<ProductDto> getProductsPerPage(Integer page) {		
	Page<ProductEntity> paginator = productRepository.findAll(PageRequest.of(page, 5));
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
