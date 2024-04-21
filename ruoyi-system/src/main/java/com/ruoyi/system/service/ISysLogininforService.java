package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysLogininfor;

/**
 * System Access Log Status Information of service.
 * 
 * @author ruoyi
 */
public interface ISysLogininforService
{
    /**
     * Add new system logs.
     * 
     * @param logininfor Visit the Log Objects
     */
    public void insertLogininfor(SysLogininfor logininfor);

    /**
     * Search system log collection.
     * 
     * @param logininfor Visit the Log Objects
     * @return Registration of records.
     */
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * Remove the system log.
     * 
     * @param infoIds Required logs to be deleted.ID
     * @return Results
     */
    public int deleteLogininforByIds(Long[] infoIds);

    /**
     * Registering system logs.
     */
    public void cleanLogininfor();
}
