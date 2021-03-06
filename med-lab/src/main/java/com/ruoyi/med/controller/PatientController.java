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
import com.ruoyi.med.domain.Patient;
import com.ruoyi.med.service.IPatientService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * PatientController
 * 
 * @author cuong
 * @date 2021-03-01
 */
@RestController
@RequestMapping("/med/patient")
public class PatientController extends BaseController
{
    @Autowired
    private IPatientService patientService;

    /**
     * Query the list of Patient
     */
    @PreAuthorize("@ss.hasPermi('med:patient:list')")
    @GetMapping("/list")
    public TableDataInfo list(Patient patient)
    {
        startPage();
        List<Patient> list = patientService.selectPatientList(patient);
        return getDataTable(list);
    }

    /**
     * Export Patient list
     */
    @PreAuthorize("@ss.hasPermi('med:patient:export')")
    @Log(title = "Patient", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Patient patient)
    {
        List<Patient> list = patientService.selectPatientList(patient);
        ExcelUtil<Patient> util = new ExcelUtil<Patient>(Patient.class);
        return util.exportExcel(list, "patient");
    }

    /**
     * Get details of Patient
     */
    @PreAuthorize("@ss.hasPermi('med:patient:query')")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId)
    {
        return AjaxResult.success(patientService.selectPatientById(patientId));
    }

    /**
     * Add Patient
     */
    @PreAuthorize("@ss.hasPermi('med:patient:add')")
    @Log(title = "Patient", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Patient patient)
    {
        return toAjax(patientService.insertPatient(patient));
    }

    /**
     * Modify Patient
     */
    @PreAuthorize("@ss.hasPermi('med:patient:edit')")
    @Log(title = "Patient", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Patient patient)
    {
        return toAjax(patientService.updatePatient(patient));
    }

    /**
     * Delete Patient
     */
    @PreAuthorize("@ss.hasPermi('med:patient:remove')")
    @Log(title = "Patient", businessType = BusinessType.DELETE)
	@DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds)
    {
        return toAjax(patientService.deletePatientByIds(patientIds));
    }
}
