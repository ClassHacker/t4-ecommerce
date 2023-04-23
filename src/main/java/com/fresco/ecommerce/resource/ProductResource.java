package com.fresco.ecommerce.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.ecommerce.models.Product;
import com.fresco.ecommerce.servcie.ProductService;

@RestController
public class ProductResource {
	
	@Autowired
	ProductService productService;
	
	@GetMapping(path = "products")
	public List<Product> getAll() {
		return productService.getAllProducts();
	}
	
	@GetMapping(path = "products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		Optional<Product> product = productService.getProduct(id);
		return ResponseEntity.of(product);
	}
	
	@DeleteMapping(path = "products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
		try {
			return ResponseEntity.ok(productService.removeProduct(id));
		} catch (Exception e) {
			return ResponseEntity.of(Optional.ofNullable(null));
		}
	}

}
