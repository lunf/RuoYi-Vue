package com.ruoyi.quartz.service;

import java.util.List;
import org.quartz.SchedulerException;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.quartz.domain.SysJob;

/**
 * Time-to-Time Information Processing of service.
 * 
 * @author ruoyi
 */
public interface ISysJobService
{
    /**
     * obtainedquartzPlanning tasks of the moderator
     * 
     * @param job Modification of information
     * @return Meeting of tasks.
     */
    public List<SysJob> selectJobList(SysJob job);

    /**
     * Through the task.IDAsk for information.
     * 
     * @param jobId Conducting tasksID
     * @return Conducting the task object information
     */
    public SysJob selectJobById(Long jobId);

    /**
     * Suspension of task
     * 
     * @param job Modification of information
     * @return Results
     */
    public int pauseJob(SysJob job) throws SchedulerException;

    /**
     * Restore the task.
     * 
     * @param job Modification of information
     * @return Results
     */
    public int resumeJob(SysJob job) throws SchedulerException;

    /**
     * After removing the task.，The correspondingtriggerThey will also be removed.
     * 
     * @param job Modification of information
     * @return Results
     */
    public int deleteJob(SysJob job) throws SchedulerException;

    /**
     * Remove the data.
     * 
     * @param jobIds The task to be removed.ID
     * @return Results
     */
    public void deleteJobByIds(Long[] jobIds) throws SchedulerException;

    /**
     * Modification of Mission Condition
     * 
     * @param job Modification of information
     * @return Results
     */
    public int changeStatus(SysJob job) throws SchedulerException;

    /**
     * Execute the task immediately.
     * 
     * @param job Modification of information
     * @return Results
     */
    public boolean run(SysJob job) throws SchedulerException;

    /**
     * Added tasks
     * 
     * @param job Modification of information
     * @return Results
     */
    public int insertJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * Updated tasks
     * 
     * @param job Modification of information
     * @return Results
     */
    public int updateJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * ExaminationcronIs the expression effective?
     * 
     * @param cronExpression The expression
     * @return Results
     */
    public boolean checkCronExpressionIsValid(String cronExpression);
}
