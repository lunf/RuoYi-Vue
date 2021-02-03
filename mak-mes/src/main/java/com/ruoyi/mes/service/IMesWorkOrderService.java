package com.ruoyi.mes.service;

import java.util.List;
import com.ruoyi.mes.domain.MesWorkOrder;

/**
 * Work Order Service Interface
 *
 * @author cuong
 * @date 2021-02-02
 */
public interface IMesWorkOrderService
{
    /**
     * Query Work Order
     *
     * @param workOrderId Work OrderID
     * @return Work Order
     */
    public MesWorkOrder selectMesWorkOrderById(Long workOrderId);

    /**
     * Query the list of Work Order
     *
     * @param mesWorkOrder Work Order
     * @return Work Order collection
     */
    public List<MesWorkOrder> selectMesWorkOrderList(MesWorkOrder mesWorkOrder);

    /**
     * Add Work Order
     *
     * @param mesWorkOrder Work Order
     * @return result
     */
    public int insertMesWorkOrder(MesWorkOrder mesWorkOrder);

    /**
     * Modify Work Order
     *
     * @param mesWorkOrder Work Order
     * @return result
     */
    public int updateMesWorkOrder(MesWorkOrder mesWorkOrder);

    /**
     * Batch delete Work Order
     *
     * @param workOrderIds Work Order ID to be deleted
     * @return result
     */
    public int deleteMesWorkOrderByIds(Long[] workOrderIds);

    /**
     * Delete Work Order information
     *
     * @param workOrderId Work OrderID
     * @return result
     */
    public int deleteMesWorkOrderById(Long workOrderId);
}
