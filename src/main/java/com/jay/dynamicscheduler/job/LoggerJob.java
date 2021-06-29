package com.jay.dynamicscheduler.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Logger job logs the current time
 */
public class LoggerJob implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(LoggerJob.class);

    private String jobName;

    public LoggerJob(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {
        log.info("{} Running {}", jobName, Calendar.getInstance().getTime());
    }
}
