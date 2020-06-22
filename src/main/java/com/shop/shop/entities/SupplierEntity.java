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
@Table(name = "PROVEEDOR")
@Data
public class SupplierEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_SHOPS")
	@Column(name = "ID")
	private Long idSuplier;
	
	@Column(name = "NOMBRE")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
	private List<ProductEntity> products = new ArrayList<ProductEntity>();
	
}
