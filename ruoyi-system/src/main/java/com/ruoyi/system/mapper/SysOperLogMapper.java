package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysOperLog;

/**
 * Operating Diaries The Data Layer
 * 
 * @author ruoyi
 */
public interface SysOperLogMapper
{
    /**
     * Add new operating logs.
     * 
     * @param operLog Operating Log Objects
     */
    public void insertOperlog(SysOperLog operLog);

    /**
     * Search system operating log collection
     * 
     * @param operLog Operating Log Objects
     * @return Operating log collection.
     */
    public List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * Remove the system operating log.
     * 
     * @param operIds The operating logs need to be deleted.ID
     * @return Results
     */
    public int deleteOperLogByIds(Long[] operIds);

    /**
     * Find the operating log in detail.
     * 
     * @param operId OperationsID
     * @return Operating Log Objects
     */
    public SysOperLog selectOperLogById(Long operId);

    /**
     * Clean operating logs.
     */
    public void cleanOperLog();
}
