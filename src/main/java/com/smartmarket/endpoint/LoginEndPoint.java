package com.smartmarket.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.tomcat.util.codec.binary.Base64;

import com.smartmarket.authentication.Auth;
import com.smartmarket.dto.LoginDTO;
import com.smartmarket.dto.MessageDTO;
import com.smartmarket.dto.UserDTO;
import com.smartmarket.entity.User;

@Path("/login")
public class LoginEndPoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserDTO userDTO) {
		
		Auth auth = new Auth();
		User user;
		if ((user = auth.validateLogin(userDTO.getEmail(), userDTO.getPassword(), userDTO.getDeviceToken())) != null) {
			
			userDTO.setName(user.getName());	
			userDTO.setProfilePicture(Base64.encodeBase64String(user.getProfilePicture()));
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUserDTO(userDTO);
			return Response.status(200).entity(loginDTO).build();
			
		}
		return Response.status(405).entity(new MessageDTO("Email ou senha inválidos!")).build();
		
	}

}
