package com.sharath.email.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharath.email.model.Email;
import com.sharath.email.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("api/email")
public class EmailController {
	   @Autowired
	    private EmailService emailService;

	    @GetMapping
	    public List<Email> getEmails() throws MessagingException, IOException {
	        return emailService.getAllEmails();
	    }
	


}
