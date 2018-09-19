package com.friendsurance.processing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.friendsurance.backend.User;
import com.friendsurance.input.FileReader;
import com.friendsurance.mail.EmailRecipientImpl;
import com.friendsurance.mail.EmailService.MailType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nexus Axis
 */
public class ItemProcessingImpl extends ItemProcessing<List<User>, Map<EmailRecipientImpl, MailType>> {
    
    public ItemProcessingImpl(FileReader reader, ItemWriter<Map<EmailRecipientImpl, MailType>> writer) {
        super(reader, writer);
    }

    /**
     * Calculate the user's situation and get the highest priority that fits
     * @param users
     * @return 
     */
    @Override
    protected Map<EmailRecipientImpl, MailType> process(List<User> users) {
        return null;
    }
    
}
