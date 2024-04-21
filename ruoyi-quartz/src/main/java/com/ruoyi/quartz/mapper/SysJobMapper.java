package com.ruoyi.quartz.mapper;

import java.util.List;
import com.ruoyi.quartz.domain.SysJob;

/**
 * Conducting the task information The Data Layer
 * 
 * @author ruoyi
 */
public interface SysJobMapper
{
    /**
     * Meet the task logs.
     * 
     * @param job Modification of information
     * @return Operating log collection.
     */
    public List<SysJob> selectJobList(SysJob job);

    /**
     * Check out all measurement tasks
     * 
     * @return List of tasks.
     */
    public List<SysJob> selectJobAll();

    /**
     * Through ModificationIDQuestion of assignment information.
     * 
     * @param jobId The ModificationID
     * @return The role object information
     */
    public SysJob selectJobById(Long jobId);

    /**
     * Through ModificationIDDelete the task information.
     * 
     * @param jobId The ModificationID
     * @return Results
     */
    public int deleteJobById(Long jobId);

    /**
     * Delete the data of the task.
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteJobByIds(Long[] ids);

    /**
     * Modification of Task Information
     * 
     * @param job Conducting the task information
     * @return Results
     */
    public int updateJob(SysJob job);

    /**
     * Additional Task Information
     * 
     * @param job Conducting the task information
     * @return Results
     */
    public int insertJob(SysJob job);
}
