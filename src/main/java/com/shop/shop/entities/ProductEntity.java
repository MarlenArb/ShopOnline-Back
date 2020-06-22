package com.shop.shop.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name = "PRODUCTOS")
@Data
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_SHOPS")
	@Column(name = "ID")
	private Long idProduct;
	
	@Column(name = "PRECIO")
	private Double price;
	
	@Column(name = "DESCRIPCION")
	private String description;

	@ManyToOne
	private SupplierEntity supplier = new SupplierEntity();
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<OrderEntity> orders = new ArrayList<OrderEntity>();

}
