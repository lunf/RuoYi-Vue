package com.ruoyi.generator.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.generator.domain.GenTable;

/**
 * The business of service.
 * 
 * @author ruoyi
 */
public interface IGenTableService
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
     * Ask for business information.
     * 
     * @param id The businessID
     * @return Business Information
     */
    public GenTable selectGenTableById(Long id);

    /**
     * Modification of business
     * 
     * @param genTable Business Information
     * @return Results
     */
    public void updateGenTable(GenTable genTable);

    /**
     * Delete business information.
     * 
     * @param tableIds Table data needed to be deleted.ID
     * @return Results
     */
    public void deleteGenTableByIds(Long[] tableIds);

    /**
     * Create a Table
     *
     * @param sql Create the word.
     * @return Results
     */
    public boolean createTable(String sql);

    /**
     * Introduction to Table Structure
     *
     * @param tableList Introduction to Table List
     * @param operName Operating staff
     */
    public void importGenTable(List<GenTable> tableList, String operName);

    /**
     * Preview of code.
     * 
     * @param tableId Number of Table
     * @return Preview of data list
     */
    public Map<String, String> previewCode(Long tableId);

    /**
     * Create the code.（The Download Method）
     * 
     * @param tableName Name of Table
     * @return The data
     */
    public byte[] downloadCode(String tableName);

    /**
     * Create the code.（Personalized route）
     * 
     * @param tableName Name of Table
     * @return The data
     */
    public void generatorCode(String tableName);

    /**
     * Synchronize the database
     * 
     * @param tableName Name of Table
     */
    public void synchDb(String tableName);

    /**
     * Growth Generates Code（The Download Method）
     * 
     * @param tableNames Number of tables
     * @return The data
     */
    public byte[] downloadCode(String[] tableNames);

    /**
     * Modification of conservation parameters.
     * 
     * @param genTable Business Information
     */
    public void validateEdit(GenTable genTable);
}
