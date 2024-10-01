package com.controller;

import com.scheduler.MainSchedular;
import com.service.CronJobService;
import com.service.SimpleScheduleJobService;

import lombok.AllArgsConstructor;
import org.quartz.JobDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class QuartzController {
    private final MainSchedular mainSchedular;

    private final SimpleScheduleJobService simpleScheduleJobService;
    
    private final CronJobService cronJobService;

    @GetMapping("/")
    public void scheduleJob(){
    	System.out.println("called scheduled job");
    	simpleScheduleJobService.scheduleSimpleJob();
    }
    
    @GetMapping("/{cron}")
    public void scheduleJobWithCronExpression(@PathVariable("cron") String cronExpression){
    	System.out.println("Called scheduleJobWithCronExpression");
    	cronJobService.scheduleCronJob("*/10 * * ? * *");
    }
    
    @GetMapping("/{cron}/{repeat}")
    public void scheduleJobWithCronExpression(@PathVariable("cron") String cronExpression, @PathVariable("repeat") int repeatCount){
    	System.out.println("Called scheduleJobWithCronExpression with repeat count");
    	cronJobService.scheduleCronJob("*/10 * * ? * *", repeatCount);
    }

    
    @GetMapping("/getAllJobs")
    public  List<JobDetail>   getAllJobs(){
      return   mainSchedular.getAllJobDetails();
    }

    @GetMapping("/getSpecificJob/{groupName}/{jobName}")
    public JobDetail getJobDetail(@PathVariable("groupName") String groupName,
                             @PathVariable("jobName") String jobName){
        return  mainSchedular.getSpecificJobDetail(jobName, groupName);

    }
    @GetMapping("/pauseAll")
    public void pauseAll(){
        mainSchedular.pauseAll();
    }
    @GetMapping("/resumeAll")
    public void resumeAll(){
        mainSchedular.resumeAll();
    }

    @GetMapping("/pause/{groupName}/{jobName}")
    public void pauseJob(@PathVariable("groupName") String groupName,
                         @PathVariable("jobName") String jobName){
        mainSchedular.pauseSpecificJob(groupName,jobName);
    }
    @GetMapping("/resume/{groupName}/{jobName}")
    public void resumeJob(@PathVariable("groupName") String groupName,
                         @PathVariable("jobName") String jobName){
        mainSchedular.resumeSpecificJob(groupName,jobName);
    }

    @PostMapping("/deleteJob/{groupName}/{jobName}")
    public boolean deleteJob(@PathVariable("groupName") String groupName,
                          @PathVariable("jobName") String jobName){
      return  mainSchedular.deleteJob(jobName,groupName);
    }

    @PostMapping("/deleteAllJobs")
    public boolean deleteAllJobs(){
        return  mainSchedular.deleteAllJobs();
    }

    @PostMapping("/updateTrigger/{groupName}/{triggerName}")
    public boolean updateTrigger(@PathVariable("groupName") String groupName,
            @PathVariable("triggerName") String triggerName){
        return  mainSchedular.updateTrigger(groupName,triggerName);
    }


}
