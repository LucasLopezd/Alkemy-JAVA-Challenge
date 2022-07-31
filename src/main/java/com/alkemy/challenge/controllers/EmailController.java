package com.alkemy.challenge.controllers;



import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.challenge.dto.email.EmailRequestDto;
import com.alkemy.challenge.service.EmailService;
import com.sendgrid.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor	
public class EmailController {
    
    private final EmailService emailService;
	
	@PostMapping("/send")
	public ResponseEntity<String> sendCustomEmail(@RequestBody EmailRequestDto email) {
		Response response = emailService.sendCustomEmail(email);

		return (response.getStatusCode() == 200 || response.getStatusCode() == 202) 
							? new ResponseEntity<>("Email has been sent successfully, please check your inbox", OK)
							: new ResponseEntity<>("Error sending email", NOT_FOUND);
	}

}
