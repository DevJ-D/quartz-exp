package com.commonUtils;

import com.model.TriggerInfo;
import org.quartz.*;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.spi.OperableTrigger;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class CommonUtils {

    public JobDetail getJobDetail(Class className, TriggerInfo info){
        JobDataMap jobData = new JobDataMap();
        jobData.put(className.getSimpleName(),info);
        return JobBuilder.newJob(className)
                .withIdentity(className.getSimpleName(),"grp1")
                //setting storeDurably to true
                //does not allow same jobDetail instance to be stored twice, 
                //which will be a meeting in our case scheduled by an employee over a specific time
                .storeDurably(true)
                .requestRecovery(false)
                .setJobData(jobData)
                .build();
    }
    public JobDetail getJobDetail(Class className){
        return JobBuilder.newJob(className)
                .withIdentity(className.getSimpleName(),"grp1")
                //setting storeDurably to true
                //does not allow same jobDetail instance to be stored twice, 
                //which will be a meeting in our case scheduled by an employee over a specific time
                .storeDurably(true)
                .build();
    }
    public Trigger getTriggerInfoOfJob(Class className, TriggerInfo info){
      SimpleScheduleBuilder builder = SimpleScheduleBuilder
              .simpleSchedule()
              .withIntervalInMilliseconds(info.getTimeInterval());
      if(info.isRunForever()){
          builder.repeatForever();
      }else{
          builder.withRepeatCount(info.getTriggerCount());
      }
        return TriggerBuilder
                .newTrigger()
                .startAt(new Date(System.currentTimeMillis()+info.getInitialOffSet()))
                .withSchedule(builder)
                .build();
    }
    public Trigger getTriggerInfoOfJobWithPriority(Class className, TriggerInfo info){
        SimpleScheduleBuilder builder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInMilliseconds(info.getTimeInterval());
        if(info.isRunForever()){
            builder.repeatForever();
        }else{
            builder.withRepeatCount(info.getTriggerCount());
        }
        return TriggerBuilder
                .newTrigger()
                .startAt(new Date(System.currentTimeMillis()+info.getInitialOffSet()))
                .withSchedule(builder)
                .withPriority(50)
                .build();
    }
    
    public Trigger getTriggerByCronExpression(Class className,String expression){
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.add(Calendar.MINUTE, -1);
        Date startAt = calendar.getTime();
        
    	Trigger trigger =  TriggerBuilder
    			.newTrigger()
    			.withIdentity(className.getSimpleName())
    			.startAt(startAt)
    			.withSchedule(CronScheduleBuilder.cronSchedule(expression))
    			.build();

    	return trigger;
    }

    public Trigger getTriggerByCronExpressionWithFixedRepeat(Class className,String expression, Date startDate, int repeatCount){
    	
    	Calendar calendar = Calendar.getInstance(); 
    	if(startDate == null) {
    	    startDate =calendar.getTime();
    	}else {
    		calendar.setTime(startDate);
    	}
    	//TODO
    	//simulating misfires, in meeting scheduling case it will be 7 days prior 
    	//we have to set the start date of the schedule 7 days back in time
    	//calendar.add(Calendar.DATE, -7);
    	calendar.add(Calendar.MINUTE, -1);
        Date startAt = calendar.getTime();
    	
    	Trigger trigger =  TriggerBuilder
    			.newTrigger()
    			.withIdentity(className.getSimpleName())
    			//experimenting for 7 days 
    			.startAt(startAt)
    			.withSchedule(CronScheduleBuilder.cronSchedule(expression)) 
    			.build();
    	
    	Date endDate=null;
    	endDate = TriggerUtils.computeEndTimeToAllowParticularNumberOfFirings(
    			(OperableTrigger) trigger,
    			new BaseCalendar(java.util.Calendar.getInstance().getTimeZone()),
    			repeatCount);

    	if(endDate != null ) {
    		trigger = trigger.getTriggerBuilder().endAt(endDate).build();
    	}

    	return trigger;
    }

    public Trigger getTriggerByCronExpressionWithEndDate(Class className,String expression, Date startDate, Date endDate){
    	
    	Calendar calendar = Calendar.getInstance(); 
    	if(startDate == null) {
    	    startDate =calendar.getTime();
    	}else {
    		calendar.setTime(startDate);
    	}
    	//TODO
    	//simulating misfires, in meeting scheduling case it will be 7 days prior 
    	//we have to set the start date of the schedule 7 days back in time
    	//calendar.add(Calendar.DATE, -7);
    	calendar.add(Calendar.MINUTE, -5);
        Date startAt = calendar.getTime();
    	
    	Trigger trigger =  TriggerBuilder
    			.newTrigger()
    			.withIdentity(className.getSimpleName())
    			//experimenting for 7 days 
    			.startAt(startAt)
    			.endAt(endDate)
    			.withSchedule(CronScheduleBuilder.cronSchedule(expression)) 
    			.build();

    	return trigger;
    }
    
    public TriggerInfo getTriggerInfoObj(int triggerCount,boolean runForever,
                                         Long repeatValue,Long initialOffSet,String information){
        TriggerInfo info = new TriggerInfo();
        info.setRunForever(runForever);
        info.setTriggerCount(triggerCount);
        info.setInitialOffSet(initialOffSet);
        info.setTimeInterval(repeatValue);
        info.setInfo(information);
        return info;
    }


}
