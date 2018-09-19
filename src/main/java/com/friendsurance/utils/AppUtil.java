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
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

/**
 *
 * @author Nexus Axis
 */
public class AppUtil {
    
    private final static Logger LOG = Logger.getLogger(AppUtil.class);
    /**
     * This method reads the users file to bootstrap records
     * @return 
     */
    public synchronized static List<User> readFile() {
        List<User> users = new ArrayList<>();
        Path path = Paths.get(getProperties().getProperty("users_file_name"));
        if (Files.exists(path)) { // check if the users.csv file actually exists 
            try (Reader reader = Files.newBufferedReader(path); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
                Iterator<CSVRecord> records = csvParser.iterator();
                while (records.hasNext()) {
                    CSVRecord record = records.next();
                    users.add(new User(filterParameter(record.get(0), false, false), Boolean.parseBoolean(filterParameter(record.get(1), true, false)), Integer.parseInt(filterParameter(record.get(2), false, true)), Integer.parseInt(filterParameter(record.get(3), false, true))));
                }
            } catch(Exception e) {
                LOG.error("Unable to read file " + path, e);
            }
        } else 
            throw new RuntimeException("The file " + path + "does not exist");
        return users;
    }
    
    private static String filterParameter(String input, boolean hasContract, boolean numeric) {
        String returnValue = hasContract ? "false" : numeric ? "0" : "";
        return null == input || input.isEmpty() ? returnValue : input.trim();
    }
    
    /**
     * This method returns the properties object for use across the entire application
     * @return 
     */
    public static Properties getProperties() {
        Properties prop = new Properties();
        InputStream is = null;
        try {     
            is = new FileInputStream("application.properties");
            // load properties 
            prop.load(is);
            return prop;
        } catch (IOException e) {
            LOG.error("Unable to read properties", e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.error("Something went wrong while trying to read properties", e);
                }
            }
        }
        throw new RuntimeException("Property file was not found");
    }
    
    /**
     * This method calculates the various rules provided 
     * @param user
     * @return 
     */
    public static MailType getMailType(User user) {
        if (!user.hasContract() && user.getFriendsNumber() == 0 && user.getSentInvitationsNumber() == 0)
            return MailType.MAIL_TYPE_2;
        else if (!user.hasContract() && user.getFriendsNumber() > 1 && user.getSentInvitationsNumber() == 0)
            return MailType.MAIL_TYPE_3;
        else if (!user.hasContract() && user.getFriendsNumber() > 3 && user.getSentInvitationsNumber() > 1)
            return MailType.MAIL_TYPE_1;
        else if (!user.hasContract() && user.getFriendsNumber() < 3 && user.getSentInvitationsNumber() > 1)
            return MailType.MAIL_TYPE_2;
        else if (!user.hasContract() && user.getFriendsNumber() < 3 && user.getSentInvitationsNumber() > 6)
            return MailType.MAIL_TYPE_3;
        else if (user.hasContract() && user.getFriendsNumber() == 0 && user.getSentInvitationsNumber() == 0)
            return MailType.MAIL_TYPE_3;
        else if (user.hasContract() && user.getFriendsNumber() == 0 && user.getSentInvitationsNumber() > 3)
            return MailType.MAIL_TYPE_3;
        else if (user.hasContract() && user.getFriendsNumber() > 1 && user.getSentInvitationsNumber() == 0) // use case will never take effect becuase invitation number will never be null because is primitive
            return MailType.MAIL_TYPE_4;
        else if (user.hasContract() && user.getFriendsNumber() > 4 && user.getSentInvitationsNumber() > 3)
            return MailType.MAIL_TYPE_3;
        throw new RuntimeException("This user does not conform to any of the rules");
    }
    
    /**
     * Return the type for a specific priority
     * @param priority
     * @return 
     */
    public static MailType getPriorityType(String priority) {
        if (MailType.MAIL_TYPE_1.toString().contains(priority))
            return MailType.MAIL_TYPE_1;
        else if (MailType.MAIL_TYPE_2.toString().contains(priority))
            return MailType.MAIL_TYPE_2;
        else if (MailType.MAIL_TYPE_3.toString().contains(priority))
            return MailType.MAIL_TYPE_3;
        else if (MailType.MAIL_TYPE_4.toString().contains(priority))
            return MailType.MAIL_TYPE_4;
        else if (MailType.MAIL_TYPE_5.toString().contains(priority))
            return MailType.MAIL_TYPE_5;
        throw new RuntimeException("Unable to identify the priority for the selected user");
    }
    
    /**
     * The body email
     * @param mailType
     * @return 
     */
    public static String emailBody(MailType mailType) { // You will find a lot of hilarious comments here, just extend the laughter :)
        switch(mailType) {
            case MAIL_TYPE_1:
                return "Hey Pal, the category configured for you is mail type 1";
            case MAIL_TYPE_2:
                return "Hey Pal, looks like teh category configured for you is mail type 2";
            case MAIL_TYPE_3:
                return "Hey Pal, looks like the category configured for you is mail type 3";
            case MAIL_TYPE_4:
                return "Hey body, looks like the category configured for you is mail type 4";
            case MAIL_TYPE_5:
                return "Hey body, you are the real big deal for being here";
            default:
                return "Looks like we cannot track you body :)";
        }
    }
}
