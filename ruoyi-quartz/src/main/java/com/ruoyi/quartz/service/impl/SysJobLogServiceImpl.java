package com.ruoyi.quartz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.quartz.domain.SysJobLog;
import com.ruoyi.quartz.mapper.SysJobLogMapper;
import com.ruoyi.quartz.service.ISysJobLogService;

/**
 * Time-to-time task conversion of log information of service.
 * 
 * @author ruoyi
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService
{
    @Autowired
    private SysJobLogMapper jobLogMapper;

    /**
     * obtainedquartzPlanning tasks of the moderators log
     * 
     * @param jobLog Modification of log information
     * @return Meet the task logs.
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog)
    {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * through the task log.IDAsk for information.
     * 
     * @param jobLogId Modification of task logsID
     * @return Conduct task log objects information
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId)
    {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * Add new task logs.
     * 
     * @param jobLog Modification of log information
     */
    @Override
    public void addJobLog(SysJobLog jobLog)
    {
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * Remove the log information.
     * 
     * @param logIds Data needed to be deleted.ID
     * @return Results
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds)
    {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * Delete the task log.
     * 
     * @param jobId Modified DiaryID
     */
    @Override
    public int deleteJobLogById(Long jobId)
    {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * Empty task logs.
     */
    @Override
    public void cleanJobLog()
    {
        jobLogMapper.cleanJobLog();
    }
}
