/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.input;

import com.friendsurance.backend.User;
import com.friendsurance.utils.AppUtil;
import com.friendsurance.processing.ItemReader;
import java.util.List;

/**
 *
 * @author Nexus Axis
 */
public class FileReader implements ItemReader<List<User>> {
    /**
     * Read all user details from the file (This file must be in the classpath to be detected by the system)
     * @return 
     */
    @Override
    public List<User> read() {
        return AppUtil.readFile();
    }
    
}
