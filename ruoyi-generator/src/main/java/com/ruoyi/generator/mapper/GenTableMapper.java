package com.ruoyi.generator.mapper;

import java.util.List;
import com.ruoyi.generator.domain.GenTable;

/**
 * The business The Data Layer
 * 
 * @author ruoyi
 */
public interface GenTableMapper
{
    /**
     * Ask for business list.
     * 
     * @param genTable Business Information
     * @return The business collection.
     */
    public List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * Question by library list
     * 
     * @param genTable Business Information
     * @return Database collection
     */
    public List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * Question by library list
     * 
     * @param tableNames Name group
     * @return Database collection
     */
    public List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * Find all table information.
     * 
     * @return Table of information collection
     */
    public List<GenTable> selectGenTableAll();

    /**
     * The Question TableIDBusiness Information
     * 
     * @param id The businessID
     * @return Business Information
     */
    public GenTable selectGenTableById(Long id);

    /**
     * Name of business information
     * 
     * @param tableName Name of Table
     * @return Business Information
     */
    public GenTable selectGenTableByName(String tableName);

    /**
     * Added business
     * 
     * @param genTable Business Information
     * @return Results
     */
    public int insertGenTable(GenTable genTable);

    /**
     * Modification of business
     * 
     * @param genTable Business Information
     * @return Results
     */
    public int updateGenTable(GenTable genTable);

    /**
     * Delete the business.
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteGenTableByIds(Long[] ids);

    /**
     * Create a Table
     *
     * @param sql Table structure
     * @return Results
     */
    public int createTable(String sql);
}
