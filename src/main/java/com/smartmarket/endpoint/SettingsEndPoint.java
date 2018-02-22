package com.smartmarket.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.smartmarket.background.BackgroundService;
import com.smartmarket.dto.MessageDTO;
import com.smartmarket.dto.SettingsDTO;

@Path("/settings")
public class SettingsEndPoint {

	@Path("/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSettings() {
		
		SettingsDTO settingsDto = new SettingsDTO();
		settingsDto.setRepositorMode(BackgroundService.repositorMode);
		
		return Response.status(200).entity(settingsDto).build();
				
	}
	
	@Path("/set")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSettings(SettingsDTO settingsDto) {
		
		BackgroundService.repositorMode = settingsDto.isRepositorMode();
		return Response.status(200).entity(new MessageDTO("Modo repositor "+(settingsDto.isRepositorMode()?"habilitado":"desabilitado"))).build();
				
	}
	
}
