package com.restapi.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloWorldResource {
	
	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/hello-bean")
	public HelloBean helloBean() {
		return new HelloBean("Hello Bean");
	}
	
	@GetMapping("/hello-bean-path-param/{name}")
	public HelloBean helloBeanPathParam(@PathVariable String name) {
		return new HelloBean("Hello Bean, " + name);
	}
	
	@GetMapping("/hello-bean-path-param/{name}/message/{message}")
	public HelloBean helloBeanMultiPathParam(@PathVariable String name,@PathVariable String message) {
		return new HelloBean("Hello Bean, " + name + " message: "+ message);
	}

}
