package com.jay.dynamicscheduler.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create _schedule table and get all data from tables.
 */
@Component
public class ScheduleRepository {

    private static final Logger log = LoggerFactory.getLogger(ScheduleRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Create initial table with few entries in it
     */
    @PostConstruct
    void createInitData() {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE _schedule IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE _schedule(" +
                "id SERIAL, job_name VARCHAR(255), cron VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("Job1|0/30 * * * * *", "Job2|0 */1 * * * *", "Job3|0/45 * * * * *").stream()
                .map(name -> name.split("\\|"))
                .collect(Collectors.toList());

        /* Stream to print out each tuple of the list */
        splitUpNames.forEach(name -> log.info(String.format("Inserting _schedule record for %s %s", name[0], name[1])));

        /* Uses JdbcTemplate's batchUpdate operation to bulk load data */
        jdbcTemplate.batchUpdate("INSERT INTO _schedule(job_name, cron) VALUES (?,?)", splitUpNames);
    }

    /**
     * Get all jobs from database
     * @return
     */
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> allCrons =
                jdbcTemplate.queryForList("select id, job_name, cron from _schedule");
        return allCrons;
    }
}
