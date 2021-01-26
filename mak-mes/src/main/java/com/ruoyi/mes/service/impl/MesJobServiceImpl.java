package com.ruoyi.mes.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.mes.domain.MesJob;
import com.ruoyi.mes.service.IMesJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mes.mapper.MesJobMapper;

/**
 * Job Order Service Implementation
 *
 * @author cuong
 * @date 2021-01-25
 */
@Service
public class MesJobServiceImpl implements IMesJobService
{
    @Autowired
    private MesJobMapper mesJobMapper;

    /**
     * Query Job Order
     *
     * @param jobId Job OrderID
     * @return Job Order
     */
    @Override
    public MesJob selectMesJobById(Long jobId)
    {
        return mesJobMapper.selectMesJobById(jobId);
    }

    /**
     * Query the list of Job Order
     *
     * @param mesJob Job Order
     * @return Job Order
     */
    @Override
    public List<MesJob> selectMesJobList(MesJob mesJob)
    {
        return mesJobMapper.selectMesJobList(mesJob);
    }

    /**
     * Add Job Order
     *
     * @param mesJob Job Order
     * @return result
     */
    @Override
    public int insertMesJob(MesJob mesJob)
    {
        mesJob.setCreateTime(DateUtils.getNowDate());
        return mesJobMapper.insertMesJob(mesJob);
    }

    /**
     * Modify Job Order
     *
     * @param mesJob Job Order
     * @return result
     */
    @Override
    public int updateMesJob(MesJob mesJob)
    {
        mesJob.setUpdateTime(DateUtils.getNowDate());
        return mesJobMapper.updateMesJob(mesJob);
    }

    /**
     * Batch delete Job Order
     *
     * @param jobIds Job Order ID to be deleted
     * @return result
     */
    @Override
    public int deleteMesJobByIds(Long[] jobIds)
    {
        return mesJobMapper.deleteMesJobByIds(jobIds);
    }

    /**
     * Delete Job Order information
     *
     * @param jobId Job OrderID
     * @return result
     */
    @Override
    public int deleteMesJobById(Long jobId)
    {
        return mesJobMapper.deleteMesJobById(jobId);
    }
}
