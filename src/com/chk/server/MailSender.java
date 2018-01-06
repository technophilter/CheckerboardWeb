package com.chk.server;

import java.io.BufferedReader;
import java.io.*;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import javax.mail.util.*;

import org.apache.log4j.Logger;

public class MailSender {
	private static Logger logger = Logger.getLogger(MailSender.class);


	final String username = "info@talentedfinds.com";
	final String password = "Miller22";

//USED FOR TESTING!!!
//	public static void main(String[] args) {
//		MailSender m = new MailSender();
//		m.sendHTMLTest();
//	}
	
	public void sendHTMLTest(){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("info@talentedfinds.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("philnorris26@hotmail.com"));
			message.setSubject("Hey philnorris26@gmail.com");
			message.setContent("Hey Mail Crawler,"
				+ "\n\n No spam to my email, please!", "text/html");

			Transport.send(message);

			logger.debug("Mailsender Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void sendAuthToken(String toEmail, String authToken){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("info@talentedfinds.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
			message.setSubject("Welcome to TalentedFinds.com");
			message.setText("Hello and welcome to TalentedFinds.com.  My name is Phil and I'm the founder of TalntedFinds.  "
					+ "I hope to be able to help you land your dream relationship and I welcome any feedback you have to our service. \n\nI look forward to serving you!\nThanks, \nPhil \n"
					+ "\n\nYour TalentedFinds.com Info...\n\nYour User Id: " + toEmail + "\nYour Authorization Token: " + authToken 
					+ "\n\n\nDid this go to your spam box? If so, be sure to add us to your contacts or make this message as safe to receive updates and likes from other members."
					);

			Transport.send(message);

			logger.debug("mailsender done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return;
	}


	public void resendToken(String toEmail, String resetToken) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("info@talentedfinds.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
			message.setSubject("Password Reset for TalentedFinds.com");
			message.setText("Here's your registration token.  Click the link below and use the registration token to reset your password.  "
					+ "\n\nYour TalentedFinds.com Info...\n\nYour User Id: " + toEmail + "\nYour Reset Token: " + resetToken 
					+ "\n\n http://www.talentedfinds.com/newpassword.jsp"
					+ "\n\n\nDid this go to your spam box? If so, be sure to add us to your contacts or make this message as safe to receive updates and likes from other members."
					);

			Transport.send(message);

			logger.debug("mailsender done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

	
}