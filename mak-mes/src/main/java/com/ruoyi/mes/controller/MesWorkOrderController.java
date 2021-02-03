package com.ruoyi.mes.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.mes.domain.MesWorkOrder;
import com.ruoyi.mes.service.IMesWorkOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Work OrderController
 *
 * @author cuong
 * @date 2021-02-02
 */
@RestController
@RequestMapping("/mes/work")
public class MesWorkOrderController extends BaseController
{
    @Autowired
    private IMesWorkOrderService mesWorkOrderService;

    /**
     * Query the list of Work Order
     */
    @PreAuthorize("@ss.hasPermi('mes:work:list')")
    @GetMapping("/job/{jobId}")
    public TableDataInfo listByJobId(@PathVariable Long jobId)
    {
        MesWorkOrder mesWorkOrder = new MesWorkOrder();
        mesWorkOrder.setJobId(jobId);
        startPage();
        List<MesWorkOrder> list = mesWorkOrderService.selectMesWorkOrderList(mesWorkOrder);
        return getDataTable(list);
    }

    /**
     * Query the list of Work Order
     */
    @PreAuthorize("@ss.hasPermi('mes:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(MesWorkOrder mesWorkOrder)
    {
        startPage();
        List<MesWorkOrder> list = mesWorkOrderService.selectMesWorkOrderList(mesWorkOrder);
        return getDataTable(list);
    }

    /**
     * Export Work Order list
     */
    @PreAuthorize("@ss.hasPermi('mes:work:export')")
    @Log(title = "Work Order", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesWorkOrder mesWorkOrder)
    {
        List<MesWorkOrder> list = mesWorkOrderService.selectMesWorkOrderList(mesWorkOrder);
        ExcelUtil<MesWorkOrder> util = new ExcelUtil<MesWorkOrder>(MesWorkOrder.class);
        return util.exportExcel(list, "work");
    }

    /**
     * Get details of Work Order
     */
    @PreAuthorize("@ss.hasPermi('mes:work:query')")
    @GetMapping(value = "/{workOrderId}")
    public AjaxResult getInfo(@PathVariable("workOrderId") Long workOrderId)
    {
        return AjaxResult.success(mesWorkOrderService.selectMesWorkOrderById(workOrderId));
    }

    /**
     * Add Work Order
     */
    @PreAuthorize("@ss.hasPermi('mes:work:add')")
    @Log(title = "Work Order", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesWorkOrder mesWorkOrder)
    {
        mesWorkOrder.setCreateBy(SecurityUtils.getUsername());
        return toAjax(mesWorkOrderService.insertMesWorkOrder(mesWorkOrder));
    }

    /**
     * Modify Work Order
     */
    @PreAuthorize("@ss.hasPermi('mes:work:edit')")
    @Log(title = "Work Order", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesWorkOrder mesWorkOrder)
    {
        mesWorkOrder.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(mesWorkOrderService.updateMesWorkOrder(mesWorkOrder));
    }

    /**
     * Delete Work Order
     */
    @PreAuthorize("@ss.hasPermi('mes:work:remove')")
    @Log(title = "Work Order", businessType = BusinessType.DELETE)
    @DeleteMapping("/{workOrderIds}")
    public AjaxResult remove(@PathVariable Long[] workOrderIds)
    {
        return toAjax(mesWorkOrderService.deleteMesWorkOrderByIds(workOrderIds));
    }
}