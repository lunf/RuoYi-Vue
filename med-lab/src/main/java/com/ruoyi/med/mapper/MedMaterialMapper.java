package com.ruoyi.med.mapper;

import java.util.List;
import com.ruoyi.med.domain.MedMaterial;

/**
 * Material Mapper Interface
 *
 * @author cuong
 * @date 2021-03-06
 */
public interface MedMaterialMapper
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
     * Delete Material
     *
     * @param materialId MaterialID
     * @return result
     */
    int deleteMedMaterialById(Long materialId);

    /**
     * Batch delete Material
     *
     * @param materialIds ID of the data to be deleted
     * @return result
     */
    int deleteMedMaterialByIds(Long[] materialIds);
}
