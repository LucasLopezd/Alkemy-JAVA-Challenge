package com.alkemy.challenge.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.challenge.dto.user.AuthenticationDto;
import com.alkemy.challenge.dto.user.AuthenticationResponseDto;
import com.alkemy.challenge.dto.user.RegisterDto;
import com.alkemy.challenge.models.User;
//import com.alkemy.challenge.service.EmailService;
import com.alkemy.challenge.service.UserService;
//import com.sendgrid.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
 
	private final UserService userService;
	//private final EmailService emailService;

	@PostMapping("/login")  
	public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationDto dto) {
		AuthenticationResponseDto response = userService.authenticate(dto);
		return ResponseEntity.ok(response);
}
   
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterDto dto) {		
		return new ResponseEntity<>(userService.create(dto), CREATED);		
	}

	/*
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
		Response response = emailService.sendDefaultEmail(dto.getEmail());

		if(response.getStatusCode() == 200 || response.getStatusCode() == 202){
			userService.create(dto);
			return new ResponseEntity<>("Successful registration, please check your inbox", CREATED);
		}
		return new ResponseEntity<>("Registration error", BAD_REQUEST);
	}
	*/
}
