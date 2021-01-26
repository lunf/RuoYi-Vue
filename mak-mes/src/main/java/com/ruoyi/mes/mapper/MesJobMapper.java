package com.ruoyi.mes.mapper;

import java.util.List;
import com.ruoyi.mes.domain.MesJob;

/**
 * Job Order Mapper Interface
 *
 * @author cuong
 * @date 2021-01-25
 */
public interface MesJobMapper
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
     * Delete Job Order
     *
     * @param jobId Job OrderID
     * @return result
     */
    public int deleteMesJobById(Long jobId);

    /**
     * Batch delete Job Order
     *
     * @param jobIds ID of the data to be deleted
     * @return result
     */
    public int deleteMesJobByIds(Long[] jobIds);
}
