package com.ruoyi.quartz.domain;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.quartz.util.CronUtils;

/**
 * timely tasks. sys_job
 * 
 * @author ruoyi
 */
public class SysJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** The taskID */
    @Excel(name = "Number of task.", cellType = ColumnType.NUMERIC)
    private Long jobId;

    /** Name of task */
    @Excel(name = "Name of task")
    private String jobName;

    /** The task group name */
    @Excel(name = "The task group name")
    private String jobGroup;

    /** Call the target string. */
    @Excel(name = "Call the target string.")
    private String invokeTarget;

    /** cronImplementation of expression */
    @Excel(name = "Implementation of expression ")
    private String cronExpression;

    /** cronPlanning Strategy */
    @Excel(name = "Planning Strategy ", readConverterExp = "0=presumed,1=Immediately trigger execution.,2=Provoking one execution.,3=Do not trigger immediate implementation.")
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /** Is it executed（0permitted 1Prohibited） */
    @Excel(name = "Joint implementation", readConverterExp = "0=permitted,1=Prohibited")
    private String concurrent;

    /** Status of task.（0Normal 1suspended） */
    @Excel(name = "Status of task.", readConverterExp = "0=Normal,1=suspended")
    private String status;

    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
    }

    @NotBlank(message = "The name of the task cannot be empty.")
    @Size(min = 0, max = 64, message = "The name of the task cannot exceed.64A character.")
    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobGroup()
    {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup)
    {
        this.jobGroup = jobGroup;
    }

    @NotBlank(message = "Calling the target string cannot be empty.")
    @Size(min = 0, max = 500, message = "The length of the target string should not exceed.500A character.")
    public String getInvokeTarget()
    {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget)
    {
        this.invokeTarget = invokeTarget;
    }

    @NotBlank(message = "CronExpression cannot be empty.")
    @Size(min = 0, max = 255, message = "CronExpression cannot exceed.255A character.")
    public String getCronExpression()
    {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getNextValidTime()
    {
        if (StringUtils.isNotEmpty(cronExpression))
        {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }

    public String getMisfirePolicy()
    {
        return misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy)
    {
        this.misfirePolicy = misfirePolicy;
    }

    public String getConcurrent()
    {
        return concurrent;
    }

    public void setConcurrent(String concurrent)
    {
        this.concurrent = concurrent;
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
            .append("jobId", getJobId())
            .append("jobName", getJobName())
            .append("jobGroup", getJobGroup())
            .append("cronExpression", getCronExpression())
            .append("nextValidTime", getNextValidTime())
            .append("misfirePolicy", getMisfirePolicy())
            .append("concurrent", getConcurrent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
