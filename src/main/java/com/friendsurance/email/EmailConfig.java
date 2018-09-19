/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.email;

import com.friendsurance.utils.AppUtil;
import com.friendsurance.mail.EmailRecipient;
import com.friendsurance.mail.EmailService;
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
import org.apache.log4j.Logger;

/**
 *
 * @author Nexus Axis
 */
public class EmailConfig implements EmailService {
    private final static Logger LOG = Logger.getLogger(EmailConfig.class);
    /**
     * Configuration Properties
     * @return 
     */
    private Properties getProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", AppUtil.getProperties().getProperty("email_tls"));
        prop.put("mail.smtp.host", AppUtil.getProperties().getProperty("email_host"));
        prop.put("mail.smtp.port", AppUtil.getProperties().getProperty("email_port"));
        prop.put("mail.smtp.ssl.enable", false);
        prop.put("mail.debug", true);
        return prop;
    }
    
    /**
     * Setting up of the email session
     * @return 
     */
    private Session getSession() {
        Session session = Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppUtil.getProperties().getProperty("username"), AppUtil.getProperties().getProperty("password"));
            }
        });
        return session;
    }

    @Override
    public void sendMail(EmailRecipient recipient, MailType mailType) {
        synchronized(this) {
            String msg = AppUtil.emailBody(mailType);
            try {
                Message message = new MimeMessage(getSession());
                message.setFrom(new InternetAddress((String) AppUtil.getProperties().get("email_from")));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.getEmail()));
                message.setSubject((String) AppUtil.getProperties().get("email_subject"));

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(msg, "text/html");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);

                message.setContent(multipart);

                Transport.send(message);
                LOG.info("Email successfully sent to " + recipient.getEmail());
            } catch (MessagingException e) {
                LOG.error("Unable to send email to the user with email address " + recipient.getEmail(), e);
            }
        }
    }
}
