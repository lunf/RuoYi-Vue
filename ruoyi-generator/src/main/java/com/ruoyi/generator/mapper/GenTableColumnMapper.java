package com.ruoyi.generator.mapper;

import java.util.List;
import com.ruoyi.generator.domain.GenTableColumn;

/**
 * The business field. The Data Layer
 * 
 * @author ruoyi
 */
public interface GenTableColumnMapper
{
    /**
     * Ask for information according to the name of the table.
     * 
     * @param tableName Name of Table
     * @return List of information
     */
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);

    /**
     * List of business fields.
     * 
     * @param tableId Business Field Number
     * @return Business fields gathering
     */
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * Adding new business fields
     * 
     * @param genTableColumn Business field information
     * @return Results
     */
    public int insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * Modification of business fields
     * 
     * @param genTableColumn Business field information
     * @return Results
     */
    public int updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * Remove the business field.
     * 
     * @param genTableColumns The data
     * @return Results
     */
    public int deleteGenTableColumns(List<GenTableColumn> genTableColumns);

    /**
     * Mass removal of business fields
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteGenTableColumnByIds(Long[] ids);
}
