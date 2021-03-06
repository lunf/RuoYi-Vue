package com.ruoyi.med.service;

import java.util.List;
import com.ruoyi.med.domain.MedMaterial;

/**
 * Material Service Interface
 * 
 * @author cuong
 * @date 2021-03-06
 */
public interface IMedMaterialService 
{
    /**
     * Query Material
     * 
     * @param materialId MaterialID
     * @return Material
     */
    MedMaterial selectMedMaterialById(Long materialId);

    /**
     * Query the list of Material
     * 
     * @param medMaterial Material
     * @return Material collection
     */
    List<MedMaterial> selectMedMaterialList(MedMaterial medMaterial);

    /**
     * Add Material
     * 
     * @param medMaterial Material
     * @return result
     */
    int insertMedMaterial(MedMaterial medMaterial);

    /**
     * Modify Material
     * 
     * @param medMaterial Material
     * @return result
     */
    int updateMedMaterial(MedMaterial medMaterial);

    /**
     * Batch delete Material
     * 
     * @param materialIds Material ID to be deleted
     * @return result
     */
    int deleteMedMaterialByIds(Long[] materialIds);

    /**
     * Delete Material information
     * 
     * @param materialId MaterialID
     * @return result
     */
    int deleteMedMaterialById(Long materialId);
}
