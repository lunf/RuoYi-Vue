package com.ruoyi.med.service.impl;

import java.util.List;

import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.med.mapper.MedMaterialMapper;
import com.ruoyi.med.domain.MedMaterial;
import com.ruoyi.med.service.IMedMaterialService;

/**
 * Material Service Implementation
 *
 * @author cuong
 * @date 2021-03-06
 */
@Service
public class MedMaterialServiceImpl implements IMedMaterialService
{
    @Autowired
    private MedMaterialMapper medMaterialMapper;

    /**
     * Query Material
     *
     * @param materialId MaterialID
     * @return Material
     */
    @Override
    public MedMaterial selectMedMaterialById(Long materialId)
    {
        return medMaterialMapper.selectMedMaterialById(materialId);
    }

    /**
     * Query the list of Material
     *
     * @param medMaterial Material
     * @return Material
     */
    @Override
    public List<MedMaterial> selectMedMaterialList(MedMaterial medMaterial)
    {
        return medMaterialMapper.selectMedMaterialList(medMaterial);
    }

    /**
     * Add Material
     *
     * @param medMaterial Material
     * @return result
     */
    @Override
    public int insertMedMaterial(MedMaterial medMaterial)
    {
        medMaterial.setBarcode(IdUtils.fastEpochSecond());
        return medMaterialMapper.insertMedMaterial(medMaterial);
    }

    /**
     * Modify Material
     *
     * @param medMaterial Material
     * @return result
     */
    @Override
    public int updateMedMaterial(MedMaterial medMaterial)
    {
        return medMaterialMapper.updateMedMaterial(medMaterial);
    }

    /**
     * Batch delete Material
     *
     * @param materialIds Material ID to be deleted
     * @return result
     */
    @Override
    public int deleteMedMaterialByIds(Long[] materialIds)
    {
        return medMaterialMapper.deleteMedMaterialByIds(materialIds);
    }

    /**
     * Delete Material information
     *
     * @param materialId MaterialID
     * @return result
     */
    @Override
    public int deleteMedMaterialById(Long materialId)
    {
        return medMaterialMapper.deleteMedMaterialById(materialId);
    }
}
