package com.jay.dynamicscheduler.schedule;

import com.jay.dynamicscheduler.job.LoggerJob;
import com.jay.dynamicscheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * All tasks are added to the scheduler
 */
@Component
public class ScheduleManager {

    @Autowired
    private ScheduleTask manager;

    @Autowired
    private ScheduleRepository scheduleRepository;

    /**
     * A context refresh event listener.
     * Get all tasks from DB and reschedule them in case of context restarted
     */
    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() {
        List<Map<String, Object>> allSchedules = scheduleRepository.getAll();
        allSchedules.forEach(schedule ->
                manager.addTaskToScheduler((String) schedule.get("job_name"), (String) schedule.get("cron"),
                        new LoggerJob((String) schedule.get("job_name")))
        );
    }
}
