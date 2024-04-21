package com.ruoyi.generator.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.mapper.GenTableColumnMapper;

/**
 * The business field. Service level achieved
 * 
 * @author ruoyi
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService 
{
	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	/**
     * List of business fields.
     * 
     * @param tableId Business Field Number
     * @return Business fields gathering
     */
	@Override
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId)
	{
	    return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
	}
	
    /**
     * Adding new business fields
     * 
     * @param genTableColumn Business field information
     * @return Results
     */
	@Override
	public int insertGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.insertGenTableColumn(genTableColumn);
	}
	
	/**
     * Modification of business fields
     * 
     * @param genTableColumn Business field information
     * @return Results
     */
	@Override
	public int updateGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.updateGenTableColumn(genTableColumn);
	}

	/**
     * Remove the business field objects
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
	@Override
	public int deleteGenTableColumnByIds(String ids)
	{
		return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
	}
}
