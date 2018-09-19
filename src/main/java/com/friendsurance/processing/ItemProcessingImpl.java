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
import com.friendsurance.utils.AppUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author Nexus Axis
 */
public class ItemProcessingImpl extends ItemProcessing<List<User>, Map<EmailRecipientImpl, MailType>> {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
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
        Map<EmailRecipientImpl, MailType> emails = new HashMap<>();
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
        return emails;
    }
    
    /**
     * Get the mail type priority
     * @param mailType
     * @return 
     */
    private int getPrioityNumber(MailType mailType) {
        String[] splitPriorityNumber = mailType.toString().split("_");
        return Integer.valueOf(splitPriorityNumber[2]);
    }
    
}
