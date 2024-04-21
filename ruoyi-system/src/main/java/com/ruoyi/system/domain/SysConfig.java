package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * The parameter configuration table. sys_config
 * 
 * @author ruoyi
 */
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** The parameter key. */
    @Excel(name = "The parameter key.", cellType = ColumnType.NUMERIC)
    private Long configId;

    /** Name of Parameters */
    @Excel(name = "Name of Parameters")
    private String configName;

    /** The parameter name. */
    @Excel(name = "The parameter name.")
    private String configKey;

    /** The parameter value. */
    @Excel(name = "The parameter value.")
    private String configValue;

    /** The system integrated.（Yis NNo） */
    @Excel(name = "The system integrated.", readConverterExp = "Y=is,N=No")
    private String configType;

    public Long getConfigId()
    {
        return configId;
    }

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    @NotBlank(message = "The parameter name cannot be empty.")
    @Size(min = 0, max = 100, message = "The parameter name cannot exceed100A character.")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @NotBlank(message = "The parameter key name length cannot be empty.")
    @Size(min = 0, max = 100, message = "The parameter key name length cannot exceed100A character.")
    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    @NotBlank(message = "The parameter key value cannot be empty.")
    @Size(min = 0, max = 500, message = "The length of the parameter key does not exceed.500A character.")
    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
