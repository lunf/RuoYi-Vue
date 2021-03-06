package com.ruoyi.med.controller;

import java.util.List;
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
import com.ruoyi.med.domain.MedMaterial;
import com.ruoyi.med.service.IMedMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * MaterialController
 * 
 * @author cuong
 * @date 2021-03-06
 */
@RestController
@RequestMapping("/med/material")
public class MedMaterialController extends BaseController
{
    @Autowired
    private IMedMaterialService medMaterialService;

    /**
     * Query the list of Material
     */
    @PreAuthorize("@ss.hasPermi('med:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(MedMaterial medMaterial)
    {
        startPage();
        List<MedMaterial> list = medMaterialService.selectMedMaterialList(medMaterial);
        return getDataTable(list);
    }

    /**
     * Export Material list
     */
    @PreAuthorize("@ss.hasPermi('med:material:export')")
    @Log(title = "Material", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MedMaterial medMaterial)
    {
        List<MedMaterial> list = medMaterialService.selectMedMaterialList(medMaterial);
        ExcelUtil<MedMaterial> util = new ExcelUtil<MedMaterial>(MedMaterial.class);
        return util.exportExcel(list, "material");
    }

    /**
     * Get details of Material
     */
    @PreAuthorize("@ss.hasPermi('med:material:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return AjaxResult.success(medMaterialService.selectMedMaterialById(materialId));
    }

    /**
     * Add Material
     */
    @PreAuthorize("@ss.hasPermi('med:material:add')")
    @Log(title = "Material", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MedMaterial medMaterial)
    {
        return toAjax(medMaterialService.insertMedMaterial(medMaterial));
    }

    /**
     * Modify Material
     */
    @PreAuthorize("@ss.hasPermi('med:material:edit')")
    @Log(title = "Material", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MedMaterial medMaterial)
    {
        return toAjax(medMaterialService.updateMedMaterial(medMaterial));
    }

    /**
     * Delete Material
     */
    @PreAuthorize("@ss.hasPermi('med:material:remove')")
    @Log(title = "Material", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(medMaterialService.deleteMedMaterialByIds(materialIds));
    }
}
