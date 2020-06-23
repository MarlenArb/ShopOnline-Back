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
@Table(name = "CLIENTE")
@Data
public class ClientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_SHOPS")
	@Column(name = "ID")
	private Long idClient;
	
	@Column(name = "NOMBRE")
	private String clientName;
	
	@Column(name = "DIRECCION")
	private String direction;
	
	@Column(name = "EDAD")
	private Integer age;
	
	@Column(name = "GENERO")
	private String gender;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
	private List<OrderEntity> orders = new ArrayList<OrderEntity>();

}
