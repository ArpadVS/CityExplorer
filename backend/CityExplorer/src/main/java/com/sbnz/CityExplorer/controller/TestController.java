package com.sbnz.CityExplorer.controller;

import java.io.IOException;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.CityExplorer.service.IntegrationTest;

@RestController
@RequestMapping("/api/test")
@CrossOrigin
public class TestController {

	@Autowired
	IntegrationTest it;
	
	@GetMapping
	public  ResponseEntity<String> test( ) {
		return new ResponseEntity<String>("It works", HttpStatus.OK);
	}
	
	@GetMapping
	@RequestMapping("/int")
	public  ResponseEntity<String> testIntegration( ) throws MavenInvocationException, IOException {
		it.testRules();
		return new ResponseEntity<String>("Integration worked", HttpStatus.OK);
	}
	
}
