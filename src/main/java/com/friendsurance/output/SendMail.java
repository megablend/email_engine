/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.output;

import com.friendsurance.email.EmailConfig;
import com.friendsurance.mail.EmailRecipientImpl;
import com.friendsurance.mail.EmailService.MailType;
import com.friendsurance.processing.ItemWriter;
import com.friendsurance.processing.thread.ThreadService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Nexus Axis
 */
public class SendMail implements ItemWriter<Map<EmailRecipientImpl, MailType>> {
    
    private final static Logger LOG = Logger.getLogger(SendMail.class);
    
    /**
     * This methods writes to an email address
     * @param users 
     */
    @Override
    public void write(Map<EmailRecipientImpl, MailType> users) {
        final Map<String, Integer> sentMails = new HashMap<>();
        if (!users.isEmpty()) {
            for (final Map.Entry<EmailRecipientImpl, MailType> user: users.entrySet()) {
                if (null == sentMails.get(user.getKey().getEmail())) {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            EmailConfig ec = new EmailConfig();
                            ec.sendMail(user.getKey(), user.getValue());
                            sentMails.put(user.getKey().getEmail(), 1);
                        }
                    };
                    ThreadService.newProcess().execute(r); // this initiates a new process for sending mails
                }
            }
            LOG.info("Mailing Processing Engine completed successfully");
        }
    }
    
}
