package com.ruoyi.med.service;

import java.util.List;
import com.ruoyi.med.domain.Patient;

/**
 * Patient Service Interface
 * 
 * @author cuong
 * @date 2021-03-01
 */
public interface IPatientService 
{
    /**
     * Query Patient
     * 
     * @param patientId PatientID
     * @return Patient
     */
    Patient selectPatientById(Long patientId);

    /**
     * Query the list of Patient
     * 
     * @param patient Patient
     * @return Patient collection
     */
    List<Patient> selectPatientList(Patient patient);

    /**
     * Add Patient
     * 
     * @param patient Patient
     * @return result
     */
    int insertPatient(Patient patient);

    /**
     * Modify Patient
     * 
     * @param patient Patient
     * @return result
     */
    int updatePatient(Patient patient);

    /**
     * Batch delete Patient
     * 
     * @param patientIds Patient ID to be deleted
     * @return result
     */
    int deletePatientByIds(Long[] patientIds);

    /**
     * Delete Patient information
     * 
     * @param patientId PatientID
     * @return result
     */
    int deletePatientById(Long patientId);
}
