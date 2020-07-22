package com.shop.shop.dtos;

import java.util.ArrayList;
import java.util.List;

import com.shop.shop.enums.Enums;

import lombok.Data;

@Data
public class UserDto {
	
	private Long idUser;
	
	private List<Enums.rol> roles = new ArrayList<>();
	

}
