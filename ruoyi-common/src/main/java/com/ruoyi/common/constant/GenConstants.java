package com.ruoyi.common.constant;

/**
 * Code to generate general constant.
 * 
 * @author ruoyi
 */
public class GenConstants
{
    /** The Single Table（Additional removal check） */
    public static final String TPL_CRUD = "crud";

    /** The Tree（Additional removal check） */
    public static final String TPL_TREE = "tree";

    /** The master table.（Additional removal check） */
    public static final String TPL_SUB = "sub";

    /** The tree code field. */
    public static final String TREE_CODE = "treeCode";

    /** The tree code field. */
    public static final String TREE_PARENT_CODE = "treeParentCode";

    /** Name of the tree field */
    public static final String TREE_NAME = "treeName";

    /** The top menu.IDFields */
    public static final String PARENT_MENU_ID = "parentMenuId";

    /** Top Menu Name Fields */
    public static final String PARENT_MENU_NAME = "parentMenuName";

    /** Type of database string */
    public static final String[] COLUMNTYPE_STR = { "char", "varchar", "nvarchar", "varchar2" };

    /** Type of database */
    public static final String[] COLUMNTYPE_TEXT = { "tinytext", "text", "mediumtext", "longtext" };

    /** Type of Database Time */
    public static final String[] COLUMNTYPE_TIME = { "datetime", "time", "date", "timestamp" };

    /** Type of Database */
    public static final String[] COLUMNTYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bit", "bigint", "float", "double", "decimal" };

    /** Page does not need to edit fields. */
    public static final String[] COLUMNNAME_NOT_EDIT = { "id", "create_by", "create_time", "del_flag" };

    /** Page does not need to display list fields */
    public static final String[] COLUMNNAME_NOT_LIST = { "id", "create_by", "create_time", "del_flag", "update_by",
            "update_time" };

    /** Page does not need to search fields. */
    public static final String[] COLUMNNAME_NOT_QUERY = { "id", "create_by", "create_time", "del_flag", "update_by",
            "update_time", "remark" };

    /** EntityBasic fields */
    public static final String[] BASE_ENTITY = { "createBy", "createTime", "updateBy", "updateTime", "remark" };

    /** TreeBasic fields */
    public static final String[] TREE_ENTITY = { "parentName", "parentId", "orderNum", "ancestors", "children" };

    /** The text box */
    public static final String HTML_INPUT = "input";

    /** Text area */
    public static final String HTML_TEXTAREA = "textarea";

    /** The lower box. */
    public static final String HTML_SELECT = "select";

    /** The Single Selection */
    public static final String HTML_RADIO = "radio";

    /** The selection box. */
    public static final String HTML_CHECKBOX = "checkbox";

    /** Date of control */
    public static final String HTML_DATETIME = "datetime";

    /** Images of control. */
    public static final String HTML_IMAGE_UPLOAD = "imageUpload";

    /** Document to control. */
    public static final String HTML_FILE_UPLOAD = "fileUpload";

    /** The rich text control. */
    public static final String HTML_EDITOR = "editor";

    /** Type of string */
    public static final String TYPE_STRING = "String";

    /** The whole type */
    public static final String TYPE_INTEGER = "Integer";

    /** The whole type. */
    public static final String TYPE_LONG = "Long";

    /** Floating Type */
    public static final String TYPE_DOUBLE = "Double";

    /** High accuracy calculation type */
    public static final String TYPE_BIGDECIMAL = "BigDecimal";

    /** Type of Time */
    public static final String TYPE_DATE = "Date";

    /** Unclean inquiries */
    public static final String QUERY_LIKE = "LIKE";

    /** Equal inquiries */
    public static final String QUERY_EQ = "EQ";

    /** Needed */
    public static final String REQUIRE = "1";
}
