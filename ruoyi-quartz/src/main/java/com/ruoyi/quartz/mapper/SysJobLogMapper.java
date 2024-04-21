package com.ruoyi.quartz.mapper;

import java.util.List;
import com.ruoyi.quartz.domain.SysJobLog;

/**
 * Adjust the task log information. The Data Layer
 * 
 * @author ruoyi
 */
public interface SysJobLogMapper
{
    /**
     * obtainedquartzPlanning tasks of the moderators log
     * 
     * @param jobLog Modification of log information
     * @return Meet the task logs.
     */
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

    /**
     * Check out all assignment logs.
     *
     * @return List of task logs.
     */
    public List<SysJobLog> selectJobLogAll();

    /**
     * through the task log.IDAsk for information.
     * 
     * @param jobLogId Modification of task logsID
     * @return Conduct task log objects information
     */
    public SysJobLog selectJobLogById(Long jobLogId);

    /**
     * Add new task logs.
     * 
     * @param jobLog Modification of log information
     * @return Results
     */
    public int insertJobLog(SysJobLog jobLog);

    /**
     * Remove the log information.
     * 
     * @param logIds Data needed to be deleted.ID
     * @return Results
     */
    public int deleteJobLogByIds(Long[] logIds);

    /**
     * Delete the task log.
     * 
     * @param jobId Modified DiaryID
     * @return Results
     */
    public int deleteJobLogById(Long jobId);

    /**
     * Empty task logs.
     */
    public void cleanJobLog();
}
