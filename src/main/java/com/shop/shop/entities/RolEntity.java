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
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table( name = "ROL")
@Data
public class RolEntity {
	
	
	@Id //Para decirle que es una PK
	@Column(name = "ID")
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long idUser;
	
	@Column(name = "USER_ROL")
	private String rol;

	//N roles M clientes 
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "roles")
	private List<UserEntity> users = new ArrayList<UserEntity>();
}
