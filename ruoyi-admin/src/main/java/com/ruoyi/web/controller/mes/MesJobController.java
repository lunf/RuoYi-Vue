package com.ruoyi.web.controller.mes;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.mes.domain.MesJob;
import com.ruoyi.mes.service.IMesJobService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Job OrderController
 *
 * @author cuong
 * @date 2021-01-26
 */
@RestController
@RequestMapping("/mes/job")
public class MesJobController extends BaseController
{
    @Autowired
    private IMesJobService mesJobService;

    /**
     * Query the list of Job Order
     */
    @PreAuthorize("@ss.hasPermi('mes:job:list')")
    @GetMapping("/list")
    public TableDataInfo list(MesJob mesJob)
    {
        startPage();
        List<MesJob> list = mesJobService.selectMesJobList(mesJob);
        return getDataTable(list);
    }

    /**
     * Export Job Order list
     */
    @PreAuthorize("@ss.hasPermi('mes:job:export')")
    @Log(title = "Job Order", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesJob mesJob)
    {
        List<MesJob> list = mesJobService.selectMesJobList(mesJob);
        ExcelUtil<MesJob> util = new ExcelUtil<MesJob>(MesJob.class);
        return util.exportExcel(list, "job");
    }

    /**
     * Get details of Job Order
     */
    @PreAuthorize("@ss.hasPermi('mes:job:query')")
    @GetMapping(value = "/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId)
    {
        return AjaxResult.success(mesJobService.selectMesJobById(jobId));
    }

    /**
     * Add Job Order
     */
    @PreAuthorize("@ss.hasPermi('mes:job:add')")
    @Log(title = "Job Order", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesJob mesJob)
    {
        mesJob.setCreateBy(SecurityUtils.getUsername());
        return toAjax(mesJobService.insertMesJob(mesJob));
    }

    /**
     * Modify Job Order
     */
    @PreAuthorize("@ss.hasPermi('mes:job:edit')")
    @Log(title = "Job Order", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesJob mesJob)
    {
        mesJob.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(mesJobService.updateMesJob(mesJob));
    }

    /**
     * Delete Job Order
     */
    @PreAuthorize("@ss.hasPermi('mes:job:remove')")
    @Log(title = "Job Order", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobIds}")
    public AjaxResult remove(@PathVariable Long[] jobIds)
    {
        return toAjax(mesJobService.deleteMesJobByIds(jobIds));
    }
}