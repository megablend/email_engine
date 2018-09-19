/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.mail;

/**
 *
 * @author Nexus Axis
 */
public class EmailRecipientImpl implements EmailRecipient{
    private String email;
    public EmailRecipientImpl(String email) {
        this.email = email;
    }
    
    @Override
    public String getEmail() {
        return email;
    }
}
