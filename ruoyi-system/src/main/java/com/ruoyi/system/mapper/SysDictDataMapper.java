package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysDictData;

/**
 * The dictionary The Data Layer
 * 
 * @author ruoyi
 */
public interface SysDictDataMapper
{
    /**
     * Ask for dictionary data according to the terms.
     * 
     * @param dictData Dictionary data information
     * @return Dictionary data collection information
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * Question dictionary data according to dictionary type
     * 
     * @param dictType Type of dictionary
     * @return Dictionary data collection information
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * Ask for dictionary data according to dictionary type and key value
     * 
     * @param dictType Type of dictionary
     * @param dictValue Keyword Value
     * @return dictionary labels
     */
    public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * According to dictionary data.IDAsk for information
     * 
     * @param dictCode dictionary dataID
     * @return dictionary data
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * Ask for dictionary data
     * 
     * @param dictType Type of dictionary
     * @return dictionary data
     */
    public int countDictDataByType(String dictType);

    /**
     * through the dictionary.IDDelete dictionary data
     * 
     * @param dictCode dictionary dataID
     * @return Results
     */
    public int deleteDictDataById(Long dictCode);

    /**
     * Delete the dictionary data.
     * 
     * @param dictCodes The dictionary data needed to be deleted.ID
     * @return Results
     */
    public int deleteDictDataByIds(Long[] dictCodes);

    /**
     * Add new dictionary data
     * 
     * @param dictData Dictionary data information
     * @return Results
     */
    public int insertDictData(SysDictData dictData);

    /**
     * Modifying dictionary data
     * 
     * @param dictData Dictionary data information
     * @return Results
     */
    public int updateDictData(SysDictData dictData);

    /**
     * Modify the dictionary type.
     * 
     * @param oldDictType Type of Old Dictionary
     * @param newDictType New Old Dictionary Types
     * @return Results
     */
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
