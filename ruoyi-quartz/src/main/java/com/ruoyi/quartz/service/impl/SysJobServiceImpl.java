package com.ruoyi.quartz.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.mapper.SysJobMapper;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.quartz.util.CronUtils;
import com.ruoyi.quartz.util.ScheduleUtils;

/**
 * timely assignment information. of service.
 * 
 * @author ruoyi
 */
@Service
public class SysJobServiceImpl implements ISysJobService
{
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper jobMapper;

    /**
     * When the project starts，The initial timing. It is mainly to prevent manual modification of the database resulting in non-synchronization to timely task processing.（Notes：Can not manually modify the database.IDand task group name.，Otherwise it will cause dirty data.）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException
    {
        scheduler.clear();
        List<SysJob> jobList = jobMapper.selectJobAll();
        for (SysJob job : jobList)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }

    /**
     * obtainedquartzPlanning Task List of Moderators
     * 
     * @param job Modification of information
     * @return
     */
    @Override
    public List<SysJob> selectJobList(SysJob job)
    {
        return jobMapper.selectJobList(job);
    }

    /**
     * Through the task.IDAsk for information.
     * 
     * @param jobId Conducting tasksID
     * @return Conducting the task object information
     */
    @Override
    public SysJob selectJobById(Long jobId)
    {
        return jobMapper.selectJobById(jobId);
    }

    /**
     * Suspension of task
     * 
     * @param job Modification of information
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pauseJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * Restore the task.
     * 
     * @param job Modification of information
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resumeJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * After removing the task.，The correspondingtriggerThey will also be removed.
     * 
     * @param job Modification of information
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteJobById(jobId);
        if (rows > 0)
        {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * Remove the data.
     * 
     * @param jobIds The task to be removed.ID
     * @return Results
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJobByIds(Long[] jobIds) throws SchedulerException
    {
        for (Long jobId : jobIds)
        {
            SysJob job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    /**
     * Modification of Mission Condition
     * 
     * @param job Modification of information
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeStatus(SysJob job) throws SchedulerException
    {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * Execute the task immediately.
     * 
     * @param job Modification of information
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean run(SysJob job) throws SchedulerException
    {
        boolean result = false;
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        SysJob properties = selectJobById(job.getJobId());
        // Parameters
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey))
        {
            result = true;
            scheduler.triggerJob(jobKey, dataMap);
        }
        return result;
    }

    /**
     * Added tasks
     * 
     * @param job Modification of information Modification of information
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertJob(SysJob job) throws SchedulerException, TaskException
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if (rows > 0)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * Updated task time expression
     * 
     * @param job Modification of information
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateJob(SysJob job) throws SchedulerException, TaskException
    {
        SysJob properties = selectJobById(job.getJobId());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }

    /**
     * Updated tasks
     * 
     * @param job Objects of task
     * @param jobGroup The task group name
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException
    {
        Long jobId = job.getJobId();
        // Determining whether there is
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey))
        {
            // Avoid data problems when creating. Remove first.，Then perform the operation.
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    /**
     * ExaminationcronIs the expression effective?
     * 
     * @param cronExpression The expression
     * @return Results
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression)
    {
        return CronUtils.isValid(cronExpression);
    }
}
