package com.ruoyi.generator.service;

import java.util.List;
import com.ruoyi.generator.domain.GenTableColumn;

/**
 * The business field. of service.
 * 
 * @author ruoyi
 */
public interface IGenTableColumnService
{
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
     * Delete business field information
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteGenTableColumnByIds(String ids);
}
