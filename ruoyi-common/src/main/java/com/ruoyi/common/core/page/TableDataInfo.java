package com.ruoyi.common.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * Table Page Data Objects
 * 
 * @author ruoyi
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** The total record number */
    private long total;

    /** List of data */
    private List<?> rows;

    /** The state code. */
    private int code;

    /** The news content. */
    private String msg;

    /**
     * Table Data Objects
     */
    public TableDataInfo()
    {
    }

    /**
     * Separate page
     * 
     * @param list List of data
     * @param total The total record number
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
