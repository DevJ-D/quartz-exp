package com.service;

import com.commonUtils.CommonUtils;
import com.jobs.FirstJob;
import com.jobs.SecondJob;
import com.model.TriggerInfo;
import com.scheduler.MainSchedular;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.CommonDataSource;

@Service
@AllArgsConstructor
public class SimpleScheduleJobService {
  private final MainSchedular schedular;
  private final CommonUtils commonUtils;


    public void scheduleSimpleJob(){

    TriggerInfo info = commonUtils.getTriggerInfoObj(10000,
            true,1500L,1000L,"info");
    schedular.scheduleJobWithPriority(SecondJob.class,info);

  }

}

