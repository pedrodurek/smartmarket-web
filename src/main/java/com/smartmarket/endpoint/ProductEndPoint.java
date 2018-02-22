package com.smartmarket.endpoint;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.smartmarket.dao.ProductDAO;
import com.smartmarket.dto.MessageDTO;
import com.smartmarket.dto.ProductDTO;
import com.smartmarket.dto.ProductIDDTO;
import com.smartmarket.dto.ProductsSyncDTO;
import com.smartmarket.entity.Product;
import com.smartmarket.hibernate.FactoryDAO;
import com.smartmarket.utils.DateUtils;

@Path("/product")
public class ProductEndPoint {
	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(ProductDTO productDTO) {
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		ProductDAO productDAO = factory.getProductDAO();
		
		Product product = new Product();
		fillProductObject(product, productDTO);
		product.setDateLastUpdate(new Date());
		productDAO.persist(product);
		productDAO.commit();
		return Response.status(200).entity(new ProductIDDTO(product.getId(), product.getDateLastUpdate())).build();
		
	}
	
	@Path("/edit")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProduct(ProductDTO productDTO) {
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		ProductDAO productDAO = factory.getProductDAO();
		
		Product product = productDAO.findById(productDTO.getId());
		if (product != null) {
			
			fillProductObject(product, productDTO);
			productDAO.update(product);
			productDAO.commit();
			return Response.status(200).entity(new ProductIDDTO(product.getId(), product.getDateLastUpdate())).build();
			
		}
		productDAO.closeSession();
		return Response.status(405).entity(new MessageDTO("Produto inexistente!")).build();
		
	}
	
	@Path("/delete")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProduct(ProductDTO productDTO) {
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		ProductDAO productDAO = factory.getProductDAO();
		
		System.out.println(productDTO.getId());
		productDAO.delete(productDTO.getId());
		productDAO.commit();
		return Response.status(200).entity(new MessageDTO("Produto removido!")).build();
		
	}
	
	@Path("/sync")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response syncProducts(List<ProductIDDTO> productsStored) {
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		ProductDAO productDAO = factory.getProductDAO();
		
		List<Product> productsWithoutImage = productDAO.getAllProductsWithoutImage();
		List<ProductIDDTO> productsWithImage = productDAO.getAllProductsWithImage();
		
		ProductsSyncDTO productsSync = new ProductsSyncDTO(true);
		
		boolean found = false;
		for (Product product: productsWithoutImage) {
			
			for (ProductIDDTO productStored: productsStored) {
				
				if (product.getId() == productStored.getId()) {
					
					found = true;
					if (!DateUtils.isSameDate(product.getDateLastUpdate(), productStored.getDateLastUpdate())) {
						productsSync.addProductsWithoutImage(product);
					}
					productsStored.remove(productStored);
					break;
					
				}
				
			}
			if (!found) {
				productsSync.addProductsWithoutImage(product);
			}
			found = false;
			
		}
		
		for (ProductIDDTO product: (List<ProductIDDTO>) productsWithImage) {
			
			for (ProductIDDTO productStored: productsStored) {
				
				if (product.getId() == productStored.getId()) {
					
					found = true;
					if (!DateUtils.isSameDate(product.getDateLastUpdate(), productStored.getDateLastUpdate())) {
						productsSync.addProductsWithImage(product);
					}
					productsStored.remove(productStored);
					break;
					
				}
				
			}
			if (!found) {
				productsSync.addProductsWithImage(product);
			}
			found = false;
			
		}
		for (ProductIDDTO product: productsStored) {
			
			if (productDAO.findById(product.getId()) == null) {
				productsSync.addProductsToBeRemoved(product);
			}
			
		}
		
		productDAO.closeSession();
		return Response.status(200).entity(productsSync).build();
		
	}
	
	@Path("/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(ProductIDDTO productIDDTO) {
		
		// Create DAO for persistence
		FactoryDAO factory = new FactoryDAO();
		ProductDAO productDAO = factory.getProductDAO();
		Product product = productDAO.findById(productIDDTO.getId());
		productDAO.closeSession();
		return Response.status(200).entity(product).build();
		
		
	}
	
	private void fillProductObject(Product product, ProductDTO productDTO) {
		
		product.setActive(true);
		product.setName(productDTO.getName());
		product.setImage(productDTO.getImage());
		product.setPrice(productDTO.getPrice());
		product.setProvider(productDTO.getProvider());
		product.setWeight(productDTO.getWeight());
		
	}
	
	
	

}
