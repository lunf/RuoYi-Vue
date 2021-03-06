package com.ruoyi.med.service.impl;

import java.util.List;

import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.med.mapper.PatientMapper;
import com.ruoyi.med.domain.Patient;
import com.ruoyi.med.service.IPatientService;

/**
 * Patient Service Implementation
 * 
 * @author cuong
 * @date 2021-03-01
 */
@Service
public class PatientServiceImpl implements IPatientService 
{
    @Autowired
    private PatientMapper patientMapper;

    /**
     * Query Patient
     * 
     * @param patientId PatientID
     * @return Patient
     */
    @Override
    public Patient selectPatientById(Long patientId)
    {
        return patientMapper.selectPatientById(patientId);
    }

    /**
     * Query the list of Patient
     * 
     * @param patient Patient
     * @return Patient
     */
    @Override
    public List<Patient> selectPatientList(Patient patient)
    {
        return patientMapper.selectPatientList(patient);
    }

    /**
     * Add Patient
     * 
     * @param patient Patient
     * @return result
     */
    @Override
    public int insertPatient(Patient patient)
    {
        // Generate barcode
        patient.setBarcode(IdUtils.fastEpochSecond());
        return patientMapper.insertPatient(patient);
    }

    /**
     * Modify Patient
     * 
     * @param patient Patient
     * @return result
     */
    @Override
    public int updatePatient(Patient patient)
    {
        return patientMapper.updatePatient(patient);
    }

    /**
     * Batch delete Patient
     * 
     * @param patientIds Patient ID to be deleted
     * @return result
     */
    @Override
    public int deletePatientByIds(Long[] patientIds)
    {
        return patientMapper.deletePatientByIds(patientIds);
    }

    /**
     * Delete Patient information
     * 
     * @param patientId PatientID
     * @return result
     */
    @Override
    public int deletePatientById(Long patientId)
    {
        return patientMapper.deletePatientById(patientId);
    }
}
