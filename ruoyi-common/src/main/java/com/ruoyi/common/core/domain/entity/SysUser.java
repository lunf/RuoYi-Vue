package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

/**
 * User Objects sys_user
 * 
 * @author ruoyi
 */
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** UsersID */
    @Excel(name = "User number", cellType = ColumnType.NUMERIC, prompt = "User Number")
    private Long userId;

    /** DepartmentID */
    @Excel(name = "Department Number", type = Type.IMPORT)
    private Long deptId;

    /** User Account */
    @Excel(name = "Registration Name")
    private String userName;

    /** Users say */
    @Excel(name = "User Name")
    private String nickName;

    /** User mailbox */
    @Excel(name = "User mailbox")
    private String email;

    /** The phone number. */
    @Excel(name = "The phone number.", cellType = ColumnType.TEXT)
    private String phonenumber;

    /** User gender */
    @Excel(name = "User gender", readConverterExp = "0=The Man,1=The Woman,2=Unknown")
    private String sex;

    /** User image */
    private String avatar;

    /** The code */
    private String password;

    /** Status of Account（0Normal 1stopped） */
    @Excel(name = "Status of Account", readConverterExp = "0=Normal,1=stopped")
    private String status;

    /** Remove the label.（0representations exist. 2The representative removed.） */
    private String delFlag;

    /** Last logged in.IP */
    @Excel(name = "Last logged in.IP", type = Type.EXPORT)
    private String loginIp;

    /** Last entry time. */
    @Excel(name = "Last entry time.", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /** Department Objects */
    @Excels({
        @Excel(name = "Name of department", targetAttr = "deptName", type = Type.EXPORT),
        @Excel(name = "Head of the department", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    /** The role objects */
    private List<SysRole> roles;

    /** The role group */
    private Long[] roleIds;

    /** The Job Group */
    private Long[] postIds;

    /** The roleID */
    private Long roleId;

    public SysUser()
    {

    }

    public SysUser(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Xss(message = "Users say they cannot contain script characters.")
    @Size(min = 0, max = 30, message = "Users say the length cannot exceed.30A character.")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @Xss(message = "The user account cannot contain script characters.")
    @NotBlank(message = "User accounts cannot be empty.")
    @Size(min = 0, max = 30, message = "User account length cannot exceed.30A character.")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "The mailbox format is incorrect.")
    @Size(min = 0, max = 50, message = "The length of the mailbox should not exceed.50A character.")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "The length of the phone number should not exceed.11A character.")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .toString();
    }
}
