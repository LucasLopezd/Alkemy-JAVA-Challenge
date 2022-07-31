package com.alkemy.challenge.service;

import org.springframework.stereotype.Service;

import com.alkemy.challenge.dto.email.EmailRequestDto;
import com.alkemy.challenge.exception.BadRequestException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final SendGrid sendGrid;

	private final String FROM = "lucaslopezd@hotmail.com";
	private final String SUBJECT = "Welcome to Disney API / Alkemy";
	private final String TYPE = "text/plain";
	private final String BODY = "Here is the body of the email";


	public Response sendCustomEmail(EmailRequestDto dto){
		Mail mail = new Mail(new Email(FROM),
							dto.getSubject(),
							new Email(dto.getTo()),
							new Content(TYPE, dto.getBody()));
		
		Request request = new Request();

		Response response = null;
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			response = sendGrid.api(request);

			System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		return response;
	}

	public Response sendDefaultEmail(String toEmail){
		Mail mail = new Mail(new Email(FROM),
							SUBJECT,
							new Email(toEmail),
							new Content(TYPE, BODY));
		
		//mail.setReplyTo(new Email("lukastoto@gmail.com"));
		Request request = new Request();

		Response response = null;
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("auth/register");
			request.setBody(mail.build());
			response = sendGrid.api(request);
			
			System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
			
		} catch (Exception e) {
			throw new RuntimeException("Error sending via SendGrid to " + toEmail + ": " + response);
		}
		
		return response;
	}
}
	


