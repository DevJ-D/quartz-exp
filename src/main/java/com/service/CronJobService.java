package com.service;

import com.jobs.FirstJob;
import com.scheduler.MainSchedular;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CronJobService {
    private final MainSchedular schedular;

    //For meetings that repeat indefinitely
    public void scheduleCronJob(String cornExpression){
        schedular.scheduleJob(FirstJob.class,cornExpression);
    }

    //For meetings that repeat a certain number of times  
    public void scheduleCronJob(String cornExpression, int repeatCount){
        schedular.scheduleJob(FirstJob.class,cornExpression, repeatCount);
    }
    
}
