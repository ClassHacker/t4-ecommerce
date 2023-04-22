package com.fresco.ecommerce.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootResource {
	
	@GetMapping("/healthcheck")
	public HttpStatus healthcheck(){
		return HttpStatus.OK;
	}
}
