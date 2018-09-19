/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.mail;

import com.friendsurance.mail.EmailService.MailType;
import com.friendsurance.utils.AppUtil;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Nexus Axis
 */
public class EmailConfigTest {
    private String recipientEmail;
    
    @Before
    public void setup() {
        recipientEmail = "megablendjobs@gmail.com";
    }
    
    /**
     * Configuration Properties
     * @return 
     */
    private Properties getProperties(boolean valid) {
        Properties prop = new Properties();
        if (valid) {
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", AppUtil.getProperties().getProperty("email_tls"));
            prop.put("mail.smtp.host", AppUtil.getProperties().getProperty("email_host"));
            prop.put("mail.smtp.port", AppUtil.getProperties().getProperty("email_port"));
            prop.put("mail.smtp.ssl.enable", false);
            prop.put("mail.debug", true);
        } else {
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", AppUtil.getProperties().getProperty("email_tls"));
            prop.put("mail.smtp.host", "charles.the.man");
            prop.put("mail.smtp.port", "who.knows");
            prop.put("mail.smtp.ssl.enable", false);
            prop.put("mail.debug", true);
        }
        return prop;
    }
    
    /**
     * Setting up of the email session
     * @return 
     */
    private Session getSession(boolean valid) {
        Session session = Session.getInstance(getProperties(valid), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppUtil.getProperties().getProperty("username"), AppUtil.getProperties().getProperty("password"));
            }
        });
        return session;
    }
    
    @Test(expected = MessagingException.class)
    public void whenWrongCredentials_throwException() throws Exception {
        Message message = new MimeMessage(getSession(false));
        message.setFrom(new InternetAddress((String) AppUtil.getProperties().get("email_from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject((String) AppUtil.getProperties().get("email_subject"));

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent("zzz", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
    
    /**
     * Test case for sending mails
     * @throws Exception 
     */
    @Test
    public void whenSendEmail_thenReturnOk() throws Exception {
        String msgBody = AppUtil.emailBody(MailType.MAIL_TYPE_1);
        String msgAfterProcessing = null;
        try {
            Message message = new MimeMessage(getSession(true));
            message.setFrom(new InternetAddress((String) AppUtil.getProperties().get("email_from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject((String) AppUtil.getProperties().get("email_subject"));

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msgBody, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            msgAfterProcessing = "Email successfully sent to " + recipientEmail;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        assertEquals("Email successfully sent to " + recipientEmail, msgAfterProcessing);
        assertEquals("Hey Pal, the category configured for you is mail type 1", msgBody);
    }
}
