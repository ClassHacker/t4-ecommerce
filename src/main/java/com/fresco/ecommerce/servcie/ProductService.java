package com.fresco.ecommerce.servcie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fresco.ecommerce.models.Product;
import com.fresco.ecommerce.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productRepo;
	
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		products = productRepo.findAll();
		return products;
	}
	
	public List<Product> getAllProductsByCategory(String categoryName) {
		List<Product> products = new ArrayList<>();
		products = productRepo.findAll();
		// method #1
//		for (Product product: products) {
//			if (product.getCategory() != categoryName) {
//				products.remove(product);
//			}
//		}
		// method #2
		products.removeIf(product -> product.getCategory() != categoryName);
		return products;
	}
	
	public Optional<Product> getProduct(Integer id) {
		return productRepo.findById(id);
	}
	
	public Product addProduct(Product productToAdd) throws Exception {
		if (productRepo.existsById(productToAdd.getProductId()) == false) {
			productRepo.save(productToAdd);
			return productToAdd;
		}
		throw new Exception("A product already exists with the id:" + productToAdd.getProductId());
	}
	
	public Product updateProduct(Product productToUpdate) throws Exception {
		Optional<Product> productInDB = productRepo.findById(productToUpdate.getProductId());
		if (productInDB.isPresent()) {
			productRepo.save(productToUpdate);
			return productToUpdate;
		}
		throw new Exception("No product exists with the id:" + productToUpdate.getProductId());
	}
	
	public Product removeProduct(Integer id) throws Exception {
		Optional<Product> productInDB = productRepo.findById(id);
		if (productInDB.isPresent()) {
			productRepo.deleteById(id);
			return productInDB.get();
		}
		throw new Exception("No product exists with the id:" + id);
	}
}
