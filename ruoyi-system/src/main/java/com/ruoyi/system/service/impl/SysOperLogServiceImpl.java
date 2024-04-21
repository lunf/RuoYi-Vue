package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.mapper.SysOperLogMapper;
import com.ruoyi.system.service.ISysOperLogService;

/**
 * Operating Diaries Service level processing.
 * 
 * @author ruoyi
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService
{
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * Add new operating logs.
     * 
     * @param operLog Operating Log Objects
     */
    @Override
    public void insertOperlog(SysOperLog operLog)
    {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * Search system operating log collection
     * 
     * @param operLog Operating Log Objects
     * @return Operating log collection.
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog)
    {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * Remove the system operating log.
     * 
     * @param operIds The operating logs need to be deleted.ID
     * @return Results
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds)
    {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * Find the operating log in detail.
     * 
     * @param operId OperationsID
     * @return Operating Log Objects
     */
    @Override
    public SysOperLog selectOperLogById(Long operId)
    {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * Clean operating logs.
     */
    @Override
    public void cleanOperLog()
    {
        operLogMapper.cleanOperLog();
    }
}
