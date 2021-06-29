package com.jay.dynamicscheduler.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

/**
 * Add or remove tasks to the scheduler
 */
@Component
public class ScheduleTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private TaskScheduler taskScheduler;

    /*Save all scheduled job information in a map*/
    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    /**
     * Add a new runnable task to the scheduler
     *
     * @param jobName
     * @param cron
     * @param task
     */
    public void addTaskToScheduler(String jobName, String cron, Runnable task) {
        log.info("Scheduling Job {} with Cron {}.", jobName, cron);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task,
                new CronTrigger(cron, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(jobName, scheduledTask);
    }

    /**
     * Remove a scheduled task
     */
    public void removeTaskFromScheduler(String jobName) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobName);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobName, null);
        }
    }
}