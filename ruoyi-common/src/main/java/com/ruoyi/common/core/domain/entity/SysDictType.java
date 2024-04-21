package com.ruoyi.common.core.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Type of dictionary sys_dict_type
 * 
 * @author ruoyi
 */
public class SysDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** The dictionary key. */
    @Excel(name = "The dictionary key.", cellType = ColumnType.NUMERIC)
    private Long dictId;

    /** Name of Dictionary */
    @Excel(name = "Name of Dictionary")
    private String dictName;

    /** Type of dictionary */
    @Excel(name = "Type of dictionary")
    private String dictType;

    /** state of（0Normal 1stopped） */
    @Excel(name = "state of", readConverterExp = "0=Normal,1=stopped")
    private String status;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    @NotBlank(message = "The dictionary name cannot be empty.")
    @Size(min = 0, max = 100, message = "Dictionary type name length cannot exceed100A character.")
    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    @NotBlank(message = "Dictionary cannot be empty.")
    @Size(min = 0, max = 100, message = "The length of the dictionary type cannot exceed100A character.")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "The dictionary type must begin with letters.，and only for（Writing the letter.，The numbers，The downline）")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
