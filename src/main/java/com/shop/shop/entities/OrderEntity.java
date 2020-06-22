package com.shop.shop.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.shop.dtos.ShopDto;

import lombok.Data;

@Entity
@Table(name = "PEDIDOS")
@Data
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_SHOPS")
	@Column(name = "ID")
	private Long idOrder;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA")
	private String orderDate;
	
	@ManyToOne
	private ShopEntity shop;
	
	@ManyToOne
	private ClientEntity client = new ClientEntity();

	@ManyToMany(mappedBy = "orders")
	private List<ProductEntity> products = new ArrayList<ProductEntity>();
	
	
	

}
