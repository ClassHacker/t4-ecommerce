package com.fresco.ecommerce.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
