package com.ruoyi.generator.domain;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.ArrayUtils;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;

/**
 * The Business Table gen_table
 * 
 * @author ruoyi
 */
public class GenTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Number of */
    private Long tableId;

    /** Name of Table */
    @NotBlank(message = "The name cannot be empty.")
    private String tableName;

    /** Table Description */
    @NotBlank(message = "Table description cannot be empty.")
    private String tableComment;

    /** Name of the Father's Table */
    private String subTableName;

    /** This table relates to the external key name of the father's table. */
    private String subTableFkName;

    /** Name of entity(The first letter is written.) */
    @NotBlank(message = "The entity name cannot be empty.")
    private String className;

    /** The template used.（crudSingle operations treeTree operations subMaster of Operations.） */
    private String tplCategory;

    /** Type of front（element-uiThe model element-plusThe model） */
    private String tplWebType;

    /** Create a package route. */
    @NotBlank(message = "The package route cannot be empty.")
    private String packageName;

    /** Create a module name. */
    @NotBlank(message = "The name of the module cannot be empty.")
    private String moduleName;

    /** Create a business name. */
    @NotBlank(message = "The business name cannot be empty.")
    private String businessName;

    /** Name of Success. */
    @NotBlank(message = "Success can not be empty.")
    private String functionName;

    /** Creating a writer */
    @NotBlank(message = "The writer cannot be empty.")
    private String functionAuthor;

    /** Create the method of code.（0zipCompressed package 1Personalized route） */
    private String genType;

    /** Create the path.（Not filling the default project route） */
    private String genPath;

    /** Main Key Information */
    private GenTableColumn pkColumn;

    /** Subscription information */
    private GenTable subTable;

    /** List of information */
    @Valid
    private List<GenTableColumn> columns;

    /** Other generating options. */
    private String options;

    /** The tree code field. */
    private String treeCode;

    /** The tree code field. */
    private String treeParentCode;

    /** Name of the tree field */
    private String treeName;

    /** The top menu.IDFields */
    private String parentMenuId;

    /** Top Menu Name Fields */
    private String parentMenuName;

    public Long getTableId()
    {
        return tableId;
    }

    public void setTableId(Long tableId)
    {
        this.tableId = tableId;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableComment()
    {
        return tableComment;
    }

    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }

    public String getSubTableName()
    {
        return subTableName;
    }

    public void setSubTableName(String subTableName)
    {
        this.subTableName = subTableName;
    }

    public String getSubTableFkName()
    {
        return subTableFkName;
    }

    public void setSubTableFkName(String subTableFkName)
    {
        this.subTableFkName = subTableFkName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getTplCategory()
    {
        return tplCategory;
    }

    public void setTplCategory(String tplCategory)
    {
        this.tplCategory = tplCategory;
    }

    public String getTplWebType()
    {
        return tplWebType;
    }

    public void setTplWebType(String tplWebType)
    {
        this.tplWebType = tplWebType;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    public String getFunctionAuthor()
    {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor)
    {
        this.functionAuthor = functionAuthor;
    }

    public String getGenType()
    {
        return genType;
    }

    public void setGenType(String genType)
    {
        this.genType = genType;
    }

    public String getGenPath()
    {
        return genPath;
    }

    public void setGenPath(String genPath)
    {
        this.genPath = genPath;
    }

    public GenTableColumn getPkColumn()
    {
        return pkColumn;
    }

    public void setPkColumn(GenTableColumn pkColumn)
    {
        this.pkColumn = pkColumn;
    }

    public GenTable getSubTable()
    {
        return subTable;
    }

    public void setSubTable(GenTable subTable)
    {
        this.subTable = subTable;
    }

    public List<GenTableColumn> getColumns()
    {
        return columns;
    }

    public void setColumns(List<GenTableColumn> columns)
    {
        this.columns = columns;
    }

    public String getOptions()
    {
        return options;
    }

    public void setOptions(String options)
    {
        this.options = options;
    }

    public String getTreeCode()
    {
        return treeCode;
    }

    public void setTreeCode(String treeCode)
    {
        this.treeCode = treeCode;
    }

    public String getTreeParentCode()
    {
        return treeParentCode;
    }

    public void setTreeParentCode(String treeParentCode)
    {
        this.treeParentCode = treeParentCode;
    }

    public String getTreeName()
    {
        return treeName;
    }

    public void setTreeName(String treeName)
    {
        this.treeName = treeName;
    }

    public String getParentMenuId()
    {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId)
    {
        this.parentMenuId = parentMenuId;
    }

    public String getParentMenuName()
    {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName)
    {
        this.parentMenuName = parentMenuName;
    }

    public boolean isSub()
    {
        return isSub(this.tplCategory);
    }

    public static boolean isSub(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_SUB, tplCategory);
    }

    public boolean isTree()
    {
        return isTree(this.tplCategory);
    }

    public static boolean isTree(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public boolean isCrud()
    {
        return isCrud(this.tplCategory);
    }

    public static boolean isCrud(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
    }

    public boolean isSuperColumn(String javaField)
    {
        return isSuperColumn(this.tplCategory, javaField);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField)
    {
        if (isTree(tplCategory))
        {
            return StringUtils.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
        }
        return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
    }
}