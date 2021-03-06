package com.ruoyi.med.mapper;

import java.util.List;
import com.ruoyi.med.domain.Patient;

/**
 * Patient Mapper Interface
 * 
 * @author cuong
 * @date 2021-03-01
 */
public interface PatientMapper 
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
     * Delete Patient
     * 
     * @param patientId PatientID
     * @return result
     */
    int deletePatientById(Long patientId);

    /**
     * Batch delete Patient
     * 
     * @param patientIds ID of the data to be deleted
     * @return result
     */
    int deletePatientByIds(Long[] patientIds);

    /**
     * Query Patient
     *
     * @param barcode Barcode
     * @return Patient
     */
    Patient selectPatientByBarcode(String barcode);
}
