package com.smartmarket.dto;

import java.util.List;

import com.owlike.genson.annotation.JsonProperty;

public class LoginDTO {
	
	@JsonProperty(value="user")
	private UserDTO userDTO;
	
	@JsonProperty(value="products")
	private List<ProductDTO> productsDTO;

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public List<ProductDTO> getProductsDTO() {
		return productsDTO;
	}

	public void setProductsDTO(List<ProductDTO> productsDTO) {
		this.productsDTO = productsDTO;
	}

}
