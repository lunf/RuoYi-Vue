package com.ruoyi.generator.util;

import java.util.Arrays;
import org.apache.commons.lang3.RegExUtils;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.config.GenConfig;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;

/**
 * Code Generator Class of tools
 * 
 * @author ruoyi
 */
public class GenUtils
{
    /**
     * Initial information
     */
    public static void initTable(GenTable genTable, String operName)
    {
        genTable.setClassName(convertClassName(genTable.getTableName()));
        genTable.setPackageName(GenConfig.getPackageName());
        genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
        genTable.setBusinessName(getBusinessName(genTable.getTableName()));
        genTable.setFunctionName(replaceText(genTable.getTableComment()));
        genTable.setFunctionAuthor(GenConfig.getAuthor());
        genTable.setCreateBy(operName);
    }

    /**
     * Initialization of properties fields
     */
    public static void initColumnField(GenTableColumn column, GenTable table)
    {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        column.setTableId(table.getTableId());
        column.setCreateBy(table.getCreateBy());
        // set upjavaName of field
        column.setJavaField(StringUtils.toCamelCase(columnName));
        // Set the default type.
        column.setJavaType(GenConstants.TYPE_STRING);
        column.setQueryType(GenConstants.QUERY_EQ);

        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType))
        {
            // The length of the string exceeds500Set as a text field.
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType) ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        }
        else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType))
        {
            column.setJavaType(GenConstants.TYPE_DATE);
            column.setHtmlType(GenConstants.HTML_DATETIME);
        }
        else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType))
        {
            column.setHtmlType(GenConstants.HTML_INPUT);

            // If it is floating. Unified useBigDecimal
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0)
            {
                column.setJavaType(GenConstants.TYPE_BIGDECIMAL);
            }
            // If it is plastic.
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10)
            {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            // Long shape.
            else
            {
                column.setJavaType(GenConstants.TYPE_LONG);
            }
        }

        // Enter the field.（All fields need to be inserted.）
        column.setIsInsert(GenConstants.REQUIRE);

        // Editing fields
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk())
        {
            column.setIsEdit(GenConstants.REQUIRE);
        }
        // List of fields
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk())
        {
            column.setIsList(GenConstants.REQUIRE);
        }
        // Find the field.
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk())
        {
            column.setIsQuery(GenConstants.REQUIRE);
        }

        // Type of polls.
        if (StringUtils.endsWithIgnoreCase(columnName, "name"))
        {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // Set the status field in a single selection box.
        if (StringUtils.endsWithIgnoreCase(columnName, "status"))
        {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // Type of&Gender fields set down boxes.
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex"))
        {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
        // Image field setup image upload controls
        else if (StringUtils.endsWithIgnoreCase(columnName, "image"))
        {
            column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
        }
        // File field settings file upload controls
        else if (StringUtils.endsWithIgnoreCase(columnName, "file"))
        {
            column.setHtmlType(GenConstants.HTML_FILE_UPLOAD);
        }
        // Content fields set rich text controls.
        else if (StringUtils.endsWithIgnoreCase(columnName, "content"))
        {
            column.setHtmlType(GenConstants.HTML_EDITOR);
        }
    }

    /**
     * Does the test group contain a specified value?
     * 
     * @param arr Number of groups
     * @param targetValue Value
     * @return Is it included
     */
    public static boolean arraysContains(String[] arr, String targetValue)
    {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * Get the module name.
     * 
     * @param packageName The name
     * @return Name of Module
     */
    public static String getModuleName(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

    /**
     * Get the business name.
     * 
     * @param tableName Name of
     * @return Name of business
     */
    public static String getBusinessName(String tableName)
    {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StringUtils.substring(tableName, lastIndex + 1, nameLength);
    }

    /**
     * The name is converted toJavaCategory Name
     * 
     * @param tableName Name of Table
     * @return Category Name
     */
    public static String convertClassName(String tableName)
    {
        boolean autoRemovePre = GenConfig.getAutoRemovePre();
        String tablePrefix = GenConfig.getTablePrefix();
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix))
        {
            String[] searchList = StringUtils.split(tablePrefix, ",");
            tableName = replaceFirst(tableName, searchList);
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * Replacement of the prefix.
     * 
     * @param replacementm The replacement value.
     * @param searchList Replace the list.
     * @return
     */
    public static String replaceFirst(String replacementm, String[] searchList)
    {
        String text = replacementm;
        for (String searchString : searchList)
        {
            if (replacementm.startsWith(searchString))
            {
                text = replacementm.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * Change the keyword.
     * 
     * @param text The name needed to be replaced.
     * @return The name after replacement.
     */
    public static String replaceText(String text)
    {
        return RegExUtils.replaceAll(text, "(?:Table of|If I)", "");
    }

    /**
     * Access to database type fields
     * 
     * @param columnType Type of column
     * @return Type after cutting.
     */
    public static String getDbType(String columnType)
    {
        if (StringUtils.indexOf(columnType, "(") > 0)
        {
            return StringUtils.substringBefore(columnType, "(");
        }
        else
        {
            return columnType;
        }
    }

    /**
     * Obtain field length.
     * 
     * @param columnType Type of column
     * @return Type after cutting.
     */
    public static Integer getColumnLength(String columnType)
    {
        if (StringUtils.indexOf(columnType, "(") > 0)
        {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        }
        else
        {
            return 0;
        }
    }
}
