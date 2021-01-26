package com.ruoyi.mes.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Job Order object mes_job
 *
 * @author cuong
 * @date 2021-01-26
 */
public class MesJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Sequence ID */
    private Long jobId;

    /** Job code */
    @Excel(name = "Job code")
    private String jobCode;

    /** Purchase order */
    @Excel(name = "Purchase order")
    private String purchaseOrder;

    /** Customer name */
    @Excel(name = "Customer name")
    private String customerName;

    /** Customer address */
    @Excel(name = "Customer address")
    private String customerAddress;

    /** Customer phone */
    @Excel(name = "Customer phone")
    private String customerPhone;

    /** Job status - (0 - default, 1 - active, 2 - inactive, 3 - achieved)   */
    @Excel(name = "Job status - (0 - default, 1 - active, 2 - inactive, 3 - achieved)  ")
    private Long status;

    /** Job priority (1 - urgent, 2 - high, 3 - normal, 4 - low, 5 - trivial) */
    @Excel(name = "Job priority (1 - urgent, 2 - high, 3 - normal, 4 - low, 5 - trivial)")
    private Long priority;

    /** Job started at */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Job started at", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startAt;

    /** Job should end at */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Job should end at", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endAt;

    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
    }

    public Long getJobId()
    {
        return jobId;
    }
    public void setJobCode(String jobCode)
    {
        this.jobCode = jobCode;
    }

    public String getJobCode()
    {
        return jobCode;
    }
    public void setPurchaseOrder(String purchaseOrder)
    {
        this.purchaseOrder = purchaseOrder;
    }

    public String getPurchaseOrder()
    {
        return purchaseOrder;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerAddress(String customerAddress)
    {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAddress()
    {
        return customerAddress;
    }
    public void setCustomerPhone(String customerPhone)
    {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone()
    {
        return customerPhone;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setPriority(Long priority)
    {
        this.priority = priority;
    }

    public Long getPriority()
    {
        return priority;
    }
    public void setStartAt(Date startAt)
    {
        this.startAt = startAt;
    }

    public Date getStartAt()
    {
        return startAt;
    }
    public void setEndAt(Date endAt)
    {
        this.endAt = endAt;
    }

    public Date getEndAt()
    {
        return endAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("jobId", getJobId())
                .append("jobCode", getJobCode())
                .append("purchaseOrder", getPurchaseOrder())
                .append("customerName", getCustomerName())
                .append("customerAddress", getCustomerAddress())
                .append("customerPhone", getCustomerPhone())
                .append("status", getStatus())
                .append("priority", getPriority())
                .append("startAt", getStartAt())
                .append("endAt", getEndAt())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
