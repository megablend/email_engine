/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.utils;

import com.friendsurance.backend.User;
import com.friendsurance.mail.EmailService.MailType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Nexus Axis
 */
public class AppUtilTest {
    private String invalidPropertyFileName;
    private String validPropertyFileName;
    private String invalidUsersFile;
    private String validUsersFile;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setup() {
        invalidPropertyFileName = "/application.properties";
        validPropertyFileName = "application.properties";
        validUsersFile = "users.csv";
        invalidUsersFile = "userszz.csv";
    }
    
    /**
     * Testing for invalid property files
     * @throws Exception 
     */
    @Test(expected = IOException.class)
    public void whenLoadPropertiesWithInvalidFile_thenReturnIOException() throws Exception {
        Properties prop = new Properties();
        InputStream is = null;
        is = new FileInputStream(invalidPropertyFileName);
        // load properties 
        prop.load(is);
    }
    
    /**
     * Testing for valid property file
     * 
     */
    @Test
    public void whenLoadPropertiesWithValidFile_thenReturnOk() throws Exception {
        Properties prop = new Properties();
        InputStream is = null;
        try {     
            is = new FileInputStream(validPropertyFileName);
            // load properties 
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        assertThat(prop.getProperty("users_file_name"), equalTo("users.csv"));
    }
    
    /**
     * Test invalid users file
     * @throws Exception 
     */
    @Test
    public void whenReadFilesWithANonExistentFile_thenReturn() throws Exception {
        Path path = Paths.get(invalidUsersFile);
        assertTrue(!Files.exists(path));
    }
    
    /**
     * Test valid users file
     * @throws Exception 
     */
    @Test
    public void whenReadFilesWithValidFile_thenReturn() throws Exception {
        Path path = Paths.get(validUsersFile);
        assertTrue(Files.exists(path));
    }
    
    /**
     * Ensure that users collection is returned
     * @throws Exception 
     */
    @Test
    public void whenReadFile_thenReturnUsers() throws Exception {
        List<User> users = AppUtil.readFile();
        assertEquals(2, users.size());
    }
    
    /**
     * Ensure that the right mail type is always returned
     * @throws Exception 
     */
    @Test(expected = RuntimeException.class)
    public void whenInvalidRule_thenThrowRuntimeException() throws Exception {
        MailType mailType = AppUtil.getMailType(new User("zz@gmail.com", true, 4000, -1));
    }
    
    /**
     * Validate a valid rule
     * @throws Exception 
     */
    @Test
    public void whenValidRule_thenReturnMailType() throws Exception {
        MailType mailType = AppUtil.getMailType(new User("zz@gmail.com", false, 0, 0));
        assertEquals(MailType.MAIL_TYPE_2, mailType);
    }
    
    @Test(expected = RuntimeException.class)
    public void whenWrongPriority_throwException() throws Exception {
        MailType priority = AppUtil.getPriorityType("34");
    }
}
