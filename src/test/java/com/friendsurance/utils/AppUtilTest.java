/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
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
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setup() {
        invalidPropertyFileName = "/application.properties";
        validPropertyFileName = "appliications.properties";
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
            is = new FileInputStream("application.properties");
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
}
