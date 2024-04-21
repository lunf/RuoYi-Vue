package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;

/**
 * The dictionary The business level.
 * 
 * @author ruoyi
 */
public interface ISysDictTypeService
{
    /**
     * Type of dictionary according to conditions.
     * 
     * @param dictType Type of dictionary information
     * @return Type of dictionary information
     */
    public List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * According to all dictionary types.
     * 
     * @return Type of dictionary information
     */
    public List<SysDictType> selectDictTypeAll();

    /**
     * Question dictionary data according to dictionary type
     * 
     * @param dictType Type of dictionary
     * @return Dictionary data collection information
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * according to the dictionary type.IDAsk for information
     * 
     * @param dictId Type of dictionaryID
     * @return Type of dictionary
     */
    public SysDictType selectDictTypeById(Long dictId);

    /**
     * Information according to dictionary type
     * 
     * @param dictType Type of dictionary
     * @return Type of dictionary
     */
    public SysDictType selectDictTypeByType(String dictType);

    /**
     * Remove the dictionary information
     * 
     * @param dictIds The dictionary needed to be removed.ID
     */
    public void deleteDictTypeByIds(Long[] dictIds);

    /**
     * Loading dictionary cache data
     */
    public void loadingDictCache();

    /**
     * Clean dictionary cache data
     */
    public void clearDictCache();

    /**
     * Repair dictionary cache data
     */
    public void resetDictCache();

    /**
     * Add new stored dictionary type information
     * 
     * @param dictType Type of dictionary information
     * @return Results
     */
    public int insertDictType(SysDictType dictType);

    /**
     * Modifying Saving Dictionary Type Information
     * 
     * @param dictType Type of dictionary information
     * @return Results
     */
    public int updateDictType(SysDictType dictType);

    /**
     * Test dictionary type is unique.
     * 
     * @param dictType Type of dictionary
     * @return Results
     */
    public boolean checkDictTypeUnique(SysDictType dictType);
}
