package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * System Access Registry sys_logininfor
 * 
 * @author ruoyi
 */
public class SysLogininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "The number", cellType = ColumnType.NUMERIC)
    private Long infoId;

    /** User Account */
    @Excel(name = "User Account")
    private String userName;

    /** Registered state 0Successful 1Failure */
    @Excel(name = "Registered state", readConverterExp = "0=Successful,1=Failure")
    private String status;

    /** RegisteredIPAddressed */
    @Excel(name = "Registration Address")
    private String ipaddr;

    /** place of registration. */
    @Excel(name = "place of registration.")
    private String loginLocation;

    /** Type of browser. */
    @Excel(name = "browsers")
    private String browser;

    /** Operating system */
    @Excel(name = "Operating system")
    private String os;

    /** suggested news */
    @Excel(name = "suggested news")
    private String msg;

    /** Time of Visit */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "Time of Visit", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    public Long getInfoId()
    {
        return infoId;
    }

    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }
}
