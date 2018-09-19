/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.mail;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmailRecipientImpl other = (EmailRecipientImpl) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
    
    
}
