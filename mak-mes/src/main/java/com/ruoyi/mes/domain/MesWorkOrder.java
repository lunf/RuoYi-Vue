package com.ruoyi.mes.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Work Order object mes_work_order
 *
 * @author cuong
 * @date 2021-02-02
 */
public class MesWorkOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long workOrderId;

    /** $column.columnComment */
    private Long jobId;

    /** Description */
    @Excel(name = "Description")
    private String description;

    /** Default = 0, Ready = 1, On-Going = 2, Completed = 3 */
    @Excel(name = "Default = 0, Ready = 1, On-Going = 2, Completed = 3")
    private Long status;

    /** Cabinet Vision = 0, Excel = 1 */
    @Excel(name = "Cabinet Vision = 0, Excel = 1")
    private Long type;

    /** Sequence of the work order in the job */
    @Excel(name = "Sequence of the work order in the job")
    private Long sequence;

    /** $column.columnComment */
    @Excel(name = "Sequence of the work order in the job")
    private Long cabinetQty;

    /** $column.columnComment */
    @Excel(name = "Sequence of the work order in the job")
    private Long partQty;

    /** $column.columnComment */
    @Excel(name = "Sequence of the work order in the job")
    private String uploadFileName;

    /** Link to store the uploaded file	 */
    private String uploadFilePath;

    /** Link to processed file */
    private String processFilePath;

    public void setWorkOrderId(Long workOrderId)
    {
        this.workOrderId = workOrderId;
    }

    public Long getWorkOrderId()
    {
        return workOrderId;
    }
    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
    }

    public Long getJobId()
    {
        return jobId;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
    }
    public void setSequence(Long sequence)
    {
        this.sequence = sequence;
    }

    public Long getSequence()
    {
        return sequence;
    }
    public void setCabinetQty(Long cabinetQty)
    {
        this.cabinetQty = cabinetQty;
    }

    public Long getCabinetQty()
    {
        return cabinetQty;
    }
    public void setPartQty(Long partQty)
    {
        this.partQty = partQty;
    }

    public Long getPartQty()
    {
        return partQty;
    }
    public void setUploadFileName(String uploadFileName)
    {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadFileName()
    {
        return uploadFileName;
    }
    public void setUploadFilePath(String uploadFilePath)
    {
        this.uploadFilePath = uploadFilePath;
    }

    public String getUploadFilePath()
    {
        return uploadFilePath;
    }
    public void setProcessFilePath(String processFilePath)
    {
        this.processFilePath = processFilePath;
    }

    public String getProcessFilePath()
    {
        return processFilePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("workOrderId", getWorkOrderId())
                .append("jobId", getJobId())
                .append("description", getDescription())
                .append("status", getStatus())
                .append("type", getType())
                .append("sequence", getSequence())
                .append("cabinetQty", getCabinetQty())
                .append("partQty", getPartQty())
                .append("uploadFileName", getUploadFileName())
                .append("uploadFilePath", getUploadFilePath())
                .append("processFilePath", getProcessFilePath())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}