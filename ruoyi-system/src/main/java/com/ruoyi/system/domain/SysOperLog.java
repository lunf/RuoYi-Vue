package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Operating log records. oper_log
 * 
 * @author ruoyi
 */
public class SysOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** The Diary Key. */
    @Excel(name = "Operation number.", cellType = ColumnType.NUMERIC)
    private Long operId;

    /** Operating modules */
    @Excel(name = "Operating modules")
    private String title;

    /** Type of business（0Other 1Added 2Modified 3removed） */
    @Excel(name = "Type of business", readConverterExp = "0=Other,1=Added,2=Modified,3=removed,4=Authorized,5=Exported,6=Introduction,7=withdrawal,8=Create the code.,9=The empty data")
    private Integer businessType;

    /** Type of Business Group */
    private Integer[] businessTypes;

    /** Method of request */
    @Excel(name = "Method of request")
    private String method;

    /** Method of request */
    @Excel(name = "Method of request")
    private String requestMethod;

    /** Operating categories（0Other 1User of the Background 2Mobile User） */
    @Excel(name = "Operating categories", readConverterExp = "0=Other,1=User of the Background,2=Mobile User")
    private Integer operatorType;

    /** Operating staff */
    @Excel(name = "Operating staff")
    private String operName;

    /** Name of department */
    @Excel(name = "Name of department")
    private String deptName;

    /** requestedurl */
    @Excel(name = "requested address")
    private String operUrl;

    /** Operating Address */
    @Excel(name = "Operating Address")
    private String operIp;

    /** Location of operation. */
    @Excel(name = "Location of operation.")
    private String operLocation;

    /** Demand of Parameters */
    @Excel(name = "Demand of Parameters")
    private String operParam;

    /** Return to Parameters */
    @Excel(name = "Return to Parameters")
    private String jsonResult;

    /** Operating state（0Normal 1Unusual） */
    @Excel(name = "state of", readConverterExp = "0=Normal,1=Unusual")
    private Integer status;

    /** The wrong news. */
    @Excel(name = "The wrong news.")
    private String errorMsg;

    /** Operating time. */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "Operating time.", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /** spending time. */
    @Excel(name = "spending time.", suffix = "Mills of seconds.")
    private Long costTime;

    public Long getOperId()
    {
        return operId;
    }

    public void setOperId(Long operId)
    {
        this.operId = operId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }

    public Integer[] getBusinessTypes()
    {
        return businessTypes;
    }

    public void setBusinessTypes(Integer[] businessTypes)
    {
        this.businessTypes = businessTypes;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType()
    {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType)
    {
        this.operatorType = operatorType;
    }

    public String getOperName()
    {
        return operName;
    }

    public void setOperName(String operName)
    {
        this.operName = operName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOperUrl()
    {
        return operUrl;
    }

    public void setOperUrl(String operUrl)
    {
        this.operUrl = operUrl;
    }

    public String getOperIp()
    {
        return operIp;
    }

    public void setOperIp(String operIp)
    {
        this.operIp = operIp;
    }

    public String getOperLocation()
    {
        return operLocation;
    }

    public void setOperLocation(String operLocation)
    {
        this.operLocation = operLocation;
    }

    public String getOperParam()
    {
        return operParam;
    }

    public void setOperParam(String operParam)
    {
        this.operParam = operParam;
    }

    public String getJsonResult()
    {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult)
    {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime()
    {
        return operTime;
    }

    public void setOperTime(Date operTime)
    {
        this.operTime = operTime;
    }

    public Long getCostTime()
    {
        return costTime;
    }

    public void setCostTime(Long costTime)
    {
        this.costTime = costTime;
    }
}
