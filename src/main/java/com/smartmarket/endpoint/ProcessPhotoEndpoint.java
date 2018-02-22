package com.smartmarket.endpoint;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.smartmarket.background.BackgroundService;
import com.smartmarket.dao.ProductDAO;
import com.smartmarket.dao.UserDAO;
import com.smartmarket.dto.MessageDTO;
import com.smartmarket.dto.PhotoDTO;
import com.smartmarket.dto.ProductDTO;
import com.smartmarket.entity.User;
import com.smartmarket.hibernate.FactoryDAO;
import com.smartmarket.machinelearning.ImageClassifier;
import com.smartmarket.notification.PushNotification;
import com.smartmarket.utils.DateUtils;
import com.smartmarket.utils.FileUtils;
import com.smartmarket.utils.XMLConfig;
import com.smartmarket.utils.XMLType;


@Path("/train")
public class ProcessPhotoEndpoint {

	@Path("/predict")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response predictPhoto(PhotoDTO photoDTO) {
		
		// Create a backup of the photo received
		FileUtils.writeBytesInFile(XMLConfig.getConfig(XMLType.MLPhotosPredict)+"image-backup"+DateUtils.getCurrentDateString()+".jpg", photoDTO.getPhoto());
		
		// Process photo received
		ProductDTO productDto = new ProductDTO();
		ImageClassifier imageClassifier = new ImageClassifier();
		FactoryDAO factory = new FactoryDAO();
		
		if (!BackgroundService.repositorMode) {
			
			// Save the photo receive			
			FileUtils.writeBytesInFile(XMLConfig.getConfig(XMLType.MLPhotosPredict)+"image-break.jpg", photoDTO.getPhoto());
			
			// Execute the algorithm responsible for detecting the stock break
			imageClassifier.detectStockBreak();
			
			// Fill in the object with the results
			ProductML product = imageClassifier.getResult();
			if (product.getPorcentage() > 80.0 && product.getName().equals("ruptura")) {
				
				
				System.out.println("Stock break! "+photoDTO.getIndex());
				PushNotification push = new PushNotification();
				
				// Create DAO for getting the active users
				UserDAO userDAO = factory.getUserDAO();
				List<User> users = userDAO.findAllActiveUsers();
				for (User user: users) {
					push.sendNotification("Ruptura", "Ruptura de estoque na prateleira "+photoDTO.getIndex(), user.getDeviceToken());
				}
				userDAO.closeSession();
				
			}
			productDto.setName("breakmode");
			productDto.setPrice(0.001f);
			
		} else {
			
			// Save the photo receive
			FileUtils.writeBytesInFile(XMLConfig.getConfig(XMLType.MLPhotosPredict)+"image.jpg", photoDTO.getPhoto());
			
			// Execute the algorithm responsible for detecting the image
			imageClassifier.recognizeImage();
			
			// Fill in the object with the results
			ProductML product = imageClassifier.getResult();
			if (product.getPorcentage() > 80.0) {
				
				productDto.setName(product.getName());
				
				// Create DAO for getting the product price
				ProductDAO productDAO = factory.getProductDAO();
				
				if (!product.getName().contains("desconhecido")) {
					productDto.setPrice(productDAO.findByName("name", product.getName()).getPrice());
				} else {
					
					productDto.setName("Desconhecido");
					productDto.setPrice(0.001f);
					
				}
				productDAO.closeSession();
				
			} else {
				productDto.setName("Desconhecido");
				productDto.setPrice(0.001f);
			}
			System.out.println("Product: "+product.getName()+" Porcentage: "+product.getPorcentage());
			
		}
		FileUtils.removeFile(XMLConfig.getConfig(XMLType.MLPhotosPredict)+"image.jpg");
		
		// Send the results to hardware
		return Response.status(200).entity(productDto).build();
		
	}
	
	@Path("/store")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response storePhoto(PhotoDTO photoDTO) {
		
		if (BackgroundService.startTraning) {
			return Response.status(405).entity(new MessageDTO("Treinamento em andamento, favor aguardar.")).build();
		}
		// Create a directory with the product name
		FileUtils.createDirectory(XMLConfig.getConfig(XMLType.MLPhotosTrain)+photoDTO.getName());
		
		// Save photo received
		String photoName = photoDTO.getName()+"/"+photoDTO.getName()+Integer.toString(photoDTO.getIndex())+".jpg";
		FileUtils.writeBytesInFile(XMLConfig.getConfig(XMLType.MLPhotosTrain)+photoName, photoDTO.getPhoto());
		
		return Response.status(200).entity(new MessageDTO("Foto armazenada com sucesso!")).build();
		
	}
	
	@Path("/request")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response train() {
		
		if (BackgroundService.startTraning) {
			return Response.status(405).entity(new MessageDTO("Treinamento em andamento, favor aguardar.")).build();
		}
		BackgroundService.startTraning = true;
		return Response.status(200).entity(new MessageDTO("Iniciando treinamento!")).build();
		
	}
	
	
}