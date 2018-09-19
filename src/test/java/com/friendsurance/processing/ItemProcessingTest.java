/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.processing;

import com.friendsurance.backend.User;
import com.friendsurance.mail.EmailRecipientImpl;
import com.friendsurance.mail.EmailService;
import com.friendsurance.utils.AppUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Nexus Axis
 */
public class ItemProcessingTest {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private String validEmail;
    private String invalidEmail;
    
    @Before
    public void setup() {
        validEmail = "mega@gmail.com";
        invalidEmail = "xxxxx";
    }
    /**
     * Test case for invalid email address
     * @throws Exception 
     */
    @Test
    public void whenInvalidEmail_thenReturnFalse() throws Exception {
        assertTrue(!EMAIL_PATTERN.matcher(invalidEmail).matches());
    }
    
    /**
     * Test case for invalid email address
     * @throws Exception 
     */
    @Test
    public void whenValidEmail_thenReturnFalse() throws Exception {
        assertTrue(EMAIL_PATTERN.matcher(validEmail).matches());
    }
    
    @Test
    public void whenProcess_thenReturnUserDetails() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("mega@gmail.com", false, 0, 0));
        users.add(new User("friendsurance@gmail.com", false, 2, 0));
        Map<EmailRecipientImpl, EmailService.MailType> emails = new HashMap<>();
        for (User user : users) {
            if (EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
                int priorityNumber = getPrioityNumber(AppUtil.getMailType(user));
                EmailRecipientImpl emailRecipientImpl = new EmailRecipientImpl(user.getEmail());
                if (null == emails.get(emailRecipientImpl)) 
                    emails.put(emailRecipientImpl, AppUtil.getPriorityType(String.valueOf(priorityNumber)));
                else {
                    int currentPriorityNumber = getPrioityNumber(emails.get(emailRecipientImpl));
                    if (priorityNumber > currentPriorityNumber)
                        emails.put(emailRecipientImpl, AppUtil.getPriorityType(String.valueOf(priorityNumber)));
                }
            }
        }
        assertTrue(users.size() > 0);
        assertTrue(!emails.isEmpty());
    }
    
    /**
     * Get the mail type priority
     * @param mailType
     * @return 
     */
    private int getPrioityNumber(EmailService.MailType mailType) {
        String[] splitPriorityNumber = mailType.toString().split("_");
        return Integer.valueOf(splitPriorityNumber[2]);
    }
}
