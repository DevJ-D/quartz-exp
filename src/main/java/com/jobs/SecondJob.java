package com.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class SecondJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("Second Job fired at "+new Date(System.currentTimeMillis()) + ", scheduled time=" +  jobExecutionContext.getScheduledFireTime());
    }
}
