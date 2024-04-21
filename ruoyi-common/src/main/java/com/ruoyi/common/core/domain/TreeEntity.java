package com.ruoyi.common.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeThe Basic
 * 
 * @author ruoyi
 */
public class TreeEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Father's Menu Name */
    private String parentName;

    /** The Dad's MenuID */
    private Long parentId;

    /** Show the order. */
    private Integer orderNum;

    /** List of ancestors */
    private String ancestors;

    /** Subsection */
    private List<?> children = new ArrayList<>();

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public List<?> getChildren()
    {
        return children;
    }

    public void setChildren(List<?> children)
    {
        this.children = children;
    }
}
