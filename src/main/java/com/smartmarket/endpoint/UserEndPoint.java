package com.smartmarket.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.tomcat.util.codec.binary.Base64;

import com.smartmarket.dao.UserDAO;
import com.smartmarket.dto.MessageDTO;
import com.smartmarket.dto.UserDTO;
import com.smartmarket.entity.User;
import com.smartmarket.hibernate.FactoryDAO;

@Path("/user")
public class UserEndPoint {
	
	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(UserDTO userDTO) {
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		UserDAO userDAO = factory.getUserDAO();
		
		// Create user to be persisted
		if (!userDAO.userExist(userDTO.getEmail())) {
			
			String msg = "Cadastro efetuado com sucesso! Aguarde a confirmação do administrador.";
			User user = new User();
			user.setPassword(userDTO.getPassword());
			user.setEmail(userDTO.getEmail());
			user.setName(userDTO.getName());
			userDAO.persist(user);
			userDAO.commit();
			return Response.status(200).entity(new MessageDTO(msg)).build();
			
		}
		userDAO.closeSession();
		return Response.status(405).entity(new MessageDTO("Usuário já cadastrado!")).build();
		
	}
	
	@Path("/edit")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProfile(UserDTO userDTO) {
		
		String msg = "Dados alterados com sucesso!";
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		UserDAO userDAO = factory.getUserDAO();
		User user = userDAO.findByName("email", userDTO.getEmail());
		if (user != null) {
			
			user.setName(userDTO.getName());
			if (userDTO.getProfilePicture() != null) {
				user.setProfilePicture(Base64.decodeBase64(userDTO.getProfilePicture()));
			}
			userDAO.update(user);
			userDAO.commit();
			return Response.status(200).entity(new MessageDTO(msg)).build();
			
		} else {
			
			msg = "Usuario inexistente!";
			userDAO.closeSession();
			return Response.status(405).entity(new MessageDTO(msg)).build();
			
		}
		
	}
	
	@Path("/changepassword")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(UserDTO userDTO) {
		
		String msg = "Senha alterada com sucesso!";
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		UserDAO userDAO = factory.getUserDAO();
		User user = userDAO.findByName("email", userDTO.getEmail());
		if (user != null) {
			
			user.setPassword(userDTO.getPassword());
			userDAO.update(user);
			userDAO.commit();
			return Response.status(200).entity(new MessageDTO(msg)).build();
			
		} else {
			
			msg = "Usuario inexistente!";
			userDAO.closeSession();
			return Response.status(405).entity(new MessageDTO(msg)).build();
			
		}
		
	}

}
