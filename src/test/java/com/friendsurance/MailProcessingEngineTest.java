package com.friendsurance;

import java.util.Timer;
import java.util.TimerTask;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nexus Axis
 */
public class MailProcessingEngineTest {
    
    @Test
    public void whenRunTask_thenReturnValue() throws Exception {
        final String[] processRunning = {""};
        TimerTask task = new TimerTask() {
            public void run() {
                processRunning[0] = "The task is working";
            }
        };
        Timer timer = new Timer();

        timer.schedule(task, 1000L);
        Thread.sleep(1000L * 2); 
        timer.cancel();
        assertEquals("The task is working", processRunning[0]);
    }
}
