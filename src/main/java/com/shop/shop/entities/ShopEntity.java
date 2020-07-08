package com.shop.shop.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TIENDA")
@Data
public class ShopEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_SHOPS")
	@Column(name = "ID")
	private Long idShop;

	@Column(name = "NOMBRE")
	private String shopName;
	
	@Column(name = "CIF")
	private String CIF;
	
	@Column(name = "COLOR")
	private String color;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shop")
	private List<OrderEntity> orders = new ArrayList<OrderEntity>();

}
