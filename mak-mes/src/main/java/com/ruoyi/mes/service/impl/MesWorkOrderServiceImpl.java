package com.ruoyi.mes.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.mes.domain.MesWorkOrder;
import com.ruoyi.mes.mapper.MesWorkOrderMapper;
import com.ruoyi.mes.service.IMesWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Work Order Service Implementation
 *
 * @author cuong
 * @date 2021-02-02
 */
@Service
public class MesWorkOrderServiceImpl implements IMesWorkOrderService
{
    @Autowired
    private MesWorkOrderMapper mesWorkOrderMapper;

    /**
     * Query Work Order
     *
     * @param workOrderId Work OrderID
     * @return Work Order
     */
    @Override
    public MesWorkOrder selectMesWorkOrderById(Long workOrderId)
    {
        return mesWorkOrderMapper.selectMesWorkOrderById(workOrderId);
    }

    /**
     * Query the list of Work Order
     *
     * @param mesWorkOrder Work Order
     * @return Work Order
     */
    @Override
    public List<MesWorkOrder> selectMesWorkOrderList(MesWorkOrder mesWorkOrder)
    {
        return mesWorkOrderMapper.selectMesWorkOrderList(mesWorkOrder);
    }

    /**
     * Add Work Order
     *
     * @param mesWorkOrder Work Order
     * @return result
     */
    @Override
    public int insertMesWorkOrder(MesWorkOrder mesWorkOrder)
    {
        mesWorkOrder.setCreateTime(DateUtils.getNowDate());

        // Check sequence
        int seqId = mesWorkOrderMapper.getCurrentWorkOrderSequence(mesWorkOrder.getJobId());

        if (seqId < 1) {
            seqId = 1;
        } else {
            seqId = seqId + 1;
        }
        mesWorkOrder.setSequence((long) seqId);

        return mesWorkOrderMapper.insertMesWorkOrder(mesWorkOrder);
    }

    /**
     * Modify Work Order
     *
     * @param mesWorkOrder Work Order
     * @return result
     */
    @Override
    public int updateMesWorkOrder(MesWorkOrder mesWorkOrder)
    {
        mesWorkOrder.setUpdateTime(DateUtils.getNowDate());
        return mesWorkOrderMapper.updateMesWorkOrder(mesWorkOrder);
    }

    /**
     * Batch delete Work Order
     *
     * @param workOrderIds Work Order ID to be deleted
     * @return result
     */
    @Override
    public int deleteMesWorkOrderByIds(Long[] workOrderIds)
    {
        return mesWorkOrderMapper.deleteMesWorkOrderByIds(workOrderIds);
    }

    /**
     * Delete Work Order information
     *
     * @param workOrderId Work OrderID
     * @return result
     */
    @Override
    public int deleteMesWorkOrderById(Long workOrderId)
    {
        return mesWorkOrderMapper.deleteMesWorkOrderById(workOrderId);
    }
}
