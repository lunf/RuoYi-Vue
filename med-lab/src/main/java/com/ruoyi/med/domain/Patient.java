package com.ruoyi.med.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Patient object med-patient
 * 
 * @author cuong
 * @date 2021-03-01
 */
public class Patient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Primary key */
    private Long patientId;

    /** Full name */
    @Excel(name = "Full name")
    private String fullName;

    /** Unique generated barcode */
    private String barcode;

    /** Mobile number */
    @Excel(name = "Mobile number")
    private String mobile;

    /** Gender */
    @Excel(name = "Gender")
    private Integer sex;

    /** Date of birth */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Date of birth", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateOfBirth;

    /** National security number */
    private String nationalNumber;

    /** Home address */
    @Excel(name = "Home address")
    private String homeAddress;

    public void setPatientId(Long patientId) 
    {
        this.patientId = patientId;
    }

    public Long getPatientId() 
    {
        return patientId;
    }
    public void setFullName(String fullName) 
    {
        this.fullName = fullName;
    }

    public String getFullName() 
    {
        return fullName;
    }
    public void setBarcode(String barcode) 
    {
        this.barcode = barcode;
    }

    public String getBarcode() 
    {
        return barcode;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }
    public void setSex(Integer sex) 
    {
        this.sex = sex;
    }

    public Integer getSex() 
    {
        return sex;
    }
    public void setDateOfBirth(Date dateOfBirth) 
    {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() 
    {
        return dateOfBirth;
    }
    public void setNationalNumber(String nationalNumber) 
    {
        this.nationalNumber = nationalNumber;
    }

    public String getNationalNumber() 
    {
        return nationalNumber;
    }
    public void setHomeAddress(String homeAddress) 
    {
        this.homeAddress = homeAddress;
    }

    public String getHomeAddress() 
    {
        return homeAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("patientId", getPatientId())
            .append("fullName", getFullName())
            .append("barcode", getBarcode())
            .append("mobile", getMobile())
            .append("sex", getSex())
            .append("dateOfBirth", getDateOfBirth())
            .append("nationalNumber", getNationalNumber())
            .append("homeAddress", getHomeAddress())
            .append("remark", getRemark())
            .toString();
    }
}
