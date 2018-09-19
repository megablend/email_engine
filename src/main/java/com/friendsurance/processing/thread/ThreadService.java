/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsurance.processing.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Nexus Axis
 */
public class ThreadService {
    /**
     * Thread pool configuration
     * @return 
     */
    public static ExecutorService newProcess() {
        return ForkJoinPool.commonPool();
    }
}
