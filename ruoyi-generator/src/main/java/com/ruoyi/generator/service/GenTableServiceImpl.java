package com.ruoyi.generator.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.core.text.CharsetKit;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.mapper.GenTableColumnMapper;
import com.ruoyi.generator.mapper.GenTableMapper;
import com.ruoyi.generator.util.GenUtils;
import com.ruoyi.generator.util.VelocityInitializer;
import com.ruoyi.generator.util.VelocityUtils;

/**
 * The business Service level achieved
 * 
 * @author ruoyi
 */
@Service
public class GenTableServiceImpl implements IGenTableService
{
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * Ask for business information.
     * 
     * @param id The businessID
     * @return Business Information
     */
    @Override
    public GenTable selectGenTableById(Long id)
    {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * Ask for business list.
     * 
     * @param genTable Business Information
     * @return The business collection.
     */
    @Override
    public List<GenTable> selectGenTableList(GenTable genTable)
    {
        return genTableMapper.selectGenTableList(genTable);
    }

    /**
     * Question by library list
     * 
     * @param genTable Business Information
     * @return Database collection
     */
    @Override
    public List<GenTable> selectDbTableList(GenTable genTable)
    {
        return genTableMapper.selectDbTableList(genTable);
    }

    /**
     * Question by library list
     * 
     * @param tableNames Name group
     * @return Database collection
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames)
    {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * Find all table information.
     * 
     * @return Table of information collection
     */
    @Override
    public List<GenTable> selectGenTableAll()
    {
        return genTableMapper.selectGenTableAll();
    }

    /**
     * Modification of business
     * 
     * @param genTable Business Information
     * @return Results
     */
    @Override
    @Transactional
    public void updateGenTable(GenTable genTable)
    {
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        int row = genTableMapper.updateGenTable(genTable);
        if (row > 0)
        {
            for (GenTableColumn cenTableColumn : genTable.getColumns())
            {
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    /**
     * Delete the business object.
     * 
     * @param tableIds Data needed to be deleted.ID
     * @return Results
     */
    @Override
    @Transactional
    public void deleteGenTableByIds(Long[] tableIds)
    {
        genTableMapper.deleteGenTableByIds(tableIds);
        genTableColumnMapper.deleteGenTableColumnByIds(tableIds);
    }

    /**
     * Create a Table
     *
     * @param sql Create the word.
     * @return Results
     */
    @Override
    public boolean createTable(String sql)
    {
        return genTableMapper.createTable(sql) == 0;
    }

    /**
     * Introduction to Table Structure
     * 
     * @param tableList Introduction to Table List
     */
    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList, String operName)
    {
        try
        {
            for (GenTable table : tableList)
            {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0)
                {
                    // Save the information.
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns)
                    {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException("Introduction Failure：" + e.getMessage());
        }
    }

    /**
     * Preview of code.
     * 
     * @param tableId Number of Table
     * @return Preview of data list
     */
    @Override
    public Map<String, String> previewCode(Long tableId)
    {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // Questionnaire information
        GenTable table = genTableMapper.selectGenTableById(tableId);
        // Set up the headline information.
        setSubTable(table);
        // Set the main key information.
        setPkColumn(table);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // Get a template list.
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
        for (String template : templates)
        {
            // rendering template.
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    /**
     * Create the code.（The Download Method）
     * 
     * @param tableName Name of Table
     * @return The data
     */
    @Override
    public byte[] downloadCode(String tableName)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * Create the code.（Personalized route）
     * 
     * @param tableName Name of Table
     */
    @Override
    public void generatorCode(String tableName)
    {
        // Questionnaire information
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // Set up the headline information.
        setSubTable(table);
        // Set the main key information.
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // Get a template list.
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
        for (String template : templates)
        {
            if (!StringUtils.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm"))
            {
                // rendering template.
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, Constants.UTF8);
                tpl.merge(context, sw);
                try
                {
                    String path = getGenPath(table, template);
                    FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
                }
                catch (IOException e)
                {
                    throw new ServiceException("Rendering template failed.，Name of：" + table.getTableName());
                }
            }
        }
    }

    /**
     * Synchronize the database
     * 
     * @param tableName Name of Table
     */
    @Override
    @Transactional
    public void synchDb(String tableName)
    {
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        Map<String, GenTableColumn> tableColumnMap = tableColumns.stream().collect(Collectors.toMap(GenTableColumn::getColumnName, Function.identity()));

        List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
        if (StringUtils.isEmpty(dbTableColumns))
        {
            throw new ServiceException("The data failure.，The original structure does not exist.");
        }
        List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            GenUtils.initColumnField(column, table);
            if (tableColumnMap.containsKey(column.getColumnName()))
            {
                GenTableColumn prevColumn = tableColumnMap.get(column.getColumnName());
                column.setColumnId(prevColumn.getColumnId());
                if (column.isList())
                {
                    // If the list，Keep the search method./Options of dictionary type
                    column.setDictType(prevColumn.getDictType());
                    column.setQueryType(prevColumn.getQueryType());
                }
                if (StringUtils.isNotEmpty(prevColumn.getIsRequired()) && !column.isPk()
                        && (column.isInsert() || column.isEdit())
                        && ((column.isUsableColumn()) || (!column.isSuperColumn())))
                {
                    // If it is(Added/Modified&Not the key./No ignorance and paternity.)，Keep the reservation filled./Showing Type Options
                    column.setIsRequired(prevColumn.getIsRequired());
                    column.setHtmlType(prevColumn.getHtmlType());
                }
                genTableColumnMapper.updateGenTableColumn(column);
            }
            else
            {
                genTableColumnMapper.insertGenTableColumn(column);
            }
        });

        List<GenTableColumn> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(delColumns))
        {
            genTableColumnMapper.deleteGenTableColumns(delColumns);
        }
    }

    /**
     * Growth Generates Code（The Download Method）
     * 
     * @param tableNames Number of tables
     * @return The data
     */
    @Override
    public byte[] downloadCode(String[] tableNames)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames)
        {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * Ask for information and generate code.
     */
    private void generatorCode(String tableName, ZipOutputStream zip)
    {
        // Questionnaire information
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // Set up the headline information.
        setSubTable(table);
        // Set the main key information.
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // Get a template list.
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
        for (String template : templates)
        {
            // rendering template.
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try
            {
                // Added tozip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("Rendering template failed.，Name of：" + table.getTableName(), e);
            }
        }
    }

    /**
     * Modification of conservation parameters.
     * 
     * @param genTable Business Information
     */
    @Override
    public void validateEdit(GenTable genTable)
    {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory()))
        {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSON.parseObject(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE)))
            {
                throw new ServiceException("The tree code fields cannot be empty.");
            }
            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE)))
            {
                throw new ServiceException("The tree code fields cannot be empty.");
            }
            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME)))
            {
                throw new ServiceException("The tree name field cannot be empty.");
            }
            else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory()))
            {
                if (StringUtils.isEmpty(genTable.getSubTableName()))
                {
                    throw new ServiceException("The name of the associated subtitle cannot be empty.");
                }
                else if (StringUtils.isEmpty(genTable.getSubTableFkName()))
                {
                    throw new ServiceException("The external key name associated with the subtitle cannot be empty.");
                }
            }
        }
    }

    /**
     * Set the main key information.
     * 
     * @param table Business Table Information
     */
    public void setPkColumn(GenTable table)
    {
        for (GenTableColumn column : table.getColumns())
        {
            if (column.isPk())
            {
                table.setPkColumn(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPkColumn()))
        {
            table.setPkColumn(table.getColumns().get(0));
        }
        if (GenConstants.TPL_SUB.equals(table.getTplCategory()))
        {
            for (GenTableColumn column : table.getSubTable().getColumns())
            {
                if (column.isPk())
                {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (StringUtils.isNull(table.getSubTable().getPkColumn()))
            {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    /**
     * Set up the headline information.
     * 
     * @param table Business Table Information
     */
    public void setSubTable(GenTable table)
    {
        String subTableName = table.getSubTableName();
        if (StringUtils.isNotEmpty(subTableName))
        {
            table.setSubTable(genTableMapper.selectGenTableByName(subTableName));
        }
    }

    /**
     * Set the code to generate other options.
     * 
     * @param genTable Created objects after setting.
     */
    public void setTableFromOptions(GenTable genTable)
    {
        JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj))
        {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
            String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
            genTable.setParentMenuId(parentMenuId);
            genTable.setParentMenuName(parentMenuName);
        }
    }

    /**
     * Get the code to create an address.
     * 
     * @param table Business Table Information
     * @param template The template file route
     * @return Create an address.
     */
    public static String getGenPath(GenTable table, String template)
    {
        String genPath = table.getGenPath();
        if (StringUtils.equals(genPath, "/"))
        {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }
}