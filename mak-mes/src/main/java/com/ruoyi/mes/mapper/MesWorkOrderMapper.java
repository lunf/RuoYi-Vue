package com.ruoyi.mes.mapper;

import java.util.List;
import com.ruoyi.mes.domain.MesWorkOrder;

/**
 * Work Order Mapper Interface
 *
 * @author cuong
 * @date 2021-02-02
 */
public interface MesWorkOrderMapper
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
     * Delete Work Order
     *
     * @param workOrderId Work OrderID
     * @return result
     */
    public int deleteMesWorkOrderById(Long workOrderId);

    /**
     * Batch delete Work Order
     *
     * @param workOrderIds ID of the data to be deleted
     * @return result
     */
    public int deleteMesWorkOrderByIds(Long[] workOrderIds);
}
