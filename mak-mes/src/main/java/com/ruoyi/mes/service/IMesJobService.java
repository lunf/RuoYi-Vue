package com.ruoyi.mes.service;

import java.util.List;
import com.ruoyi.mes.domain.MesJob;

/**
 * Job Order Service Interface
 *
 * @author cuong
 * @date 2021-01-25
 */
public interface IMesJobService
{
    /**
     * Query Job Order
     *
     * @param jobId Job OrderID
     * @return Job Order
     */
    public MesJob selectMesJobById(Long jobId);

    /**
     * Query the list of Job Order
     *
     * @param mesJob Job Order
     * @return Job Order collection
     */
    public List<MesJob> selectMesJobList(MesJob mesJob);

    /**
     * Add Job Order
     *
     * @param mesJob Job Order
     * @return result
     */
    public int insertMesJob(MesJob mesJob);

    /**
     * Modify Job Order
     *
     * @param mesJob Job Order
     * @return result
     */
    public int updateMesJob(MesJob mesJob);

    /**
     * Batch delete Job Order
     *
     * @param jobIds Job Order ID to be deleted
     * @return result
     */
    public int deleteMesJobByIds(Long[] jobIds);

    /**
     * Delete Job Order information
     *
     * @param jobId Job OrderID
     * @return result
     */
    public int deleteMesJobById(Long jobId);
}