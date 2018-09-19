package com.friendsurance;


import com.friendsurance.input.FileReader;
import com.friendsurance.output.SendMail;
import com.friendsurance.processing.ItemProcessingImpl;
import com.friendsurance.utils.AppUtil;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nexus Axis
 */
public class MailProcessingEngine {
    
    private final static Logger LOG = Logger.getLogger(MailProcessingEngine.class);
    
    /**
     * This is the entry point to the application, hope you like it :)
     * @param args 
     */
    public static void main(String[] args) {
        MailProcessingEngine mailEngine = new MailProcessingEngine();
        LOG.info("Mail Processing Engine Started Successfully -- Let us catch the real action at " + AppUtil.getProperties().getProperty("email_processing_hour") + ":" + AppUtil.getProperties().getProperty("email_processing_minute") + ":" + AppUtil.getProperties().getProperty("email_processing_second") + " today :)");
        try {
            mailEngine.scheduler();
        } catch (InterruptedException e) {
            LOG.error("The process was interrupted");
        }
    }
    
    /**
     * The scheduler that runs to determine the appropriate time to trigger the email engine
     * @throws InterruptedException 
     */
    void scheduler() throws InterruptedException {
        final Timer timer = new Timer();
        final int hour = Integer.parseInt(AppUtil.getProperties().getProperty("email_processing_hour"));
        final int minute = Integer.parseInt(AppUtil.getProperties().getProperty("email_processing_minute"));
        final int second = Integer.parseInt(AppUtil.getProperties().getProperty("email_processing_second"));
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                int hourOfTheDay = cal.get(Calendar.HOUR_OF_DAY);//get the hour number of the day, from 0 to 23
                int minuteOfTheDay = cal.get(Calendar.MINUTE);
                int secondOfTheDay = cal.get(Calendar.SECOND);
                
                if (hourOfTheDay == hour && minuteOfTheDay == minute && secondOfTheDay == second) {
                    ItemProcessingImpl itemProcessing = new ItemProcessingImpl(new FileReader(), new SendMail());
                    itemProcessing.doProcessing();
                }
            }
        };
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, hour);
        today.set(Calendar.MINUTE, minute);
        today.set(Calendar.SECOND, second);
        timer.scheduleAtFixedRate(task, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }
}
