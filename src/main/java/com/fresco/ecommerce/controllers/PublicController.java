package com.fresco.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.ecommerce.models.Product;
import com.fresco.ecommerce.models.User;
import com.fresco.ecommerce.repo.ProductRepo;
import com.fresco.ecommerce.repo.UserRepo;

@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	@Autowired
	ProductRepo repo;
	
	@Autowired
	UserRepo userRepo;

	@GetMapping("/product/search")
	public List<Product> getProducts(@RequestParam("keyword") String key) {
		List<Product> products = repo.findAll();
		products.stream().filter( (product) -> {
			return product.getCategory().getCategoryName().contains(key) || product.getProductName().contains(key);
		});
		return products;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		Optional<User> validUser = userRepo.findByUsername(user.getUsername());
		if (validUser.isPresent()) {
			if (validUser.get().getPassword().equals(user.getPassword())) {
				return ResponseEntity.ok("JWT");
			}
		}
		return ResponseEntity.status(401).build();
	}

}