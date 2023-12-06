package com.sharath.email.service;

import java.io.IOException;
import java.util.List;

import com.sharath.email.model.Email;

import jakarta.mail.MessagingException;

public interface EmailService {
	List<Email> getAllEmails() throws MessagingException, IOException;

}
