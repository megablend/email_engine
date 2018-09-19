/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.processing;

import com.friendsurance.mail.EmailService;
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
