package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysDictType;

/**
 * The dictionary The Data Layer
 * 
 * @author ruoyi
 */
public interface SysDictTypeMapper
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
     * through the dictionary.IDDelete the dictionary information
     * 
     * @param dictId The dictionaryID
     * @return Results
     */
    public int deleteDictTypeById(Long dictId);

    /**
     * Delete the dictionary type information.
     * 
     * @param dictIds The dictionary needed to be removed.ID
     * @return Results
     */
    public int deleteDictTypeByIds(Long[] dictIds);

    /**
     * Add new dictionary type information
     * 
     * @param dictType Type of dictionary information
     * @return Results
     */
    public int insertDictType(SysDictType dictType);

    /**
     * Modifying dictionary type information
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
    public SysDictType checkDictTypeUnique(String dictType);
}
