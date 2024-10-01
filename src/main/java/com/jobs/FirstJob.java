package com.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
//@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class FirstJob implements Job {
	//This implements the logic to create the meeting when the trigger fires
    @Override
    public void execute(JobExecutionContext jobExecutionContext)  {
        System.out.println("First Job fired at "+new Date(System.currentTimeMillis()) + ", scheduled time=" +  jobExecutionContext.getScheduledFireTime());
        Date date = jobExecutionContext.getScheduledFireTime();
        int count = jobExecutionContext.getJobDetail().getJobDataMap().get("NumberOfTimesJobIsTriggered")==null?0:(int)jobExecutionContext.getJobDetail().getJobDataMap().get("NumberOfTimesJobIsTriggered");
        System.out.println("This job has been fired " + count + " times");
        jobExecutionContext.getJobDetail().getJobDataMap().put("NumberOfTimesJobIsTriggered",++count);
        
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        //TODO the actual meeting schedule time is 7 days ahead of the trigger fire time
        calendar.add(Calendar.DATE, 7);
        Date meetingToBeScheduledAt = calendar.getTime();
        //When the trigger fires the meeting gets scheduled exactly 7 days ahead
        System.out.println("MeetingToBeScheduled at " + meetingToBeScheduledAt);

    }
}
