package com.ruoyi.quartz.service;

import java.util.List;
import com.ruoyi.quartz.domain.SysJobLog;

/**
 * Time-to-time task conversion of log information of service.
 * 
 * @author ruoyi
 */
public interface ISysJobLogService
{
    /**
     * obtainedquartzPlanning tasks of the moderators log
     * 
     * @param jobLog Modification of log information
     * @return Meet the task logs.
     */
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

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
     */
    public void addJobLog(SysJobLog jobLog);

    /**
     * Remove the log information.
     * 
     * @param logIds The logs to be deleted.ID
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
