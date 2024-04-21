package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysDictData;

/**
 * The dictionary The business level.
 * 
 * @author ruoyi
 */
public interface ISysDictDataService
{
    /**
     * Ask for dictionary data according to the terms.
     * 
     * @param dictData Dictionary data information
     * @return Dictionary data collection information
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * Ask for dictionary data according to dictionary type and key value
     * 
     * @param dictType Type of dictionary
     * @param dictValue Keyword Value
     * @return dictionary labels
     */
    public String selectDictLabel(String dictType, String dictValue);

    /**
     * According to dictionary data.IDAsk for information
     * 
     * @param dictCode dictionary dataID
     * @return dictionary data
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * Delete the dictionary data.
     * 
     * @param dictCodes The dictionary data needed to be deleted.ID
     */
    public void deleteDictDataByIds(Long[] dictCodes);

    /**
     * Add to save dictionary data information
     * 
     * @param dictData Dictionary data information
     * @return Results
     */
    public int insertDictData(SysDictData dictData);

    /**
     * Modifying the dictionary data storage
     * 
     * @param dictData Dictionary data information
     * @return Results
     */
    public int updateDictData(SysDictData dictData);
}
