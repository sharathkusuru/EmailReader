package com.sharath.email.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharath.email.model.Email;
import com.sharath.email.repository.EmailRepository;
import com.sharath.email.service.EmailService;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailRepository emailRepository;

	@Override
	public List<Email> getAllEmails() throws MessagingException, IOException {
		Properties properties = new Properties();
		properties.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(properties, null);

		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com", "sharathkumark509@gmail.com", "lfmm bvjg epqx lsac");

		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);

		Message[] messages = inbox.getMessages();

		List<Email> emailList = new ArrayList<>();
		for (Message message : messages) {
			Email email = new Email();
			email.setSubject(message.getSubject());
			email.setSender(Arrays.toString(message.getFrom()));
			try {
				email.setContent(getTextFromMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
			}

			emailList.add(email);
		}

		emailRepository.saveAll(emailList);

		store.close();

		return emailList;
	}

	private String getTextFromMessage(Part bp2) throws Exception {
		if (bp2.isMimeType("text/plain")) {
			return bp2.getContent().toString();
		} else if (bp2.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) bp2.getContent();
			BodyPart bp = mp.getBodyPart(0);
			return getTextFromMessage((Part) bp);
		}
		return "";
	}

}
