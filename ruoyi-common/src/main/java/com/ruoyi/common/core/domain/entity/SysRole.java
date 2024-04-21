package com.ruoyi.common.core.domain.entity;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Character Table sys_role
 * 
 * @author ruoyi
 */
public class SysRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** The roleID */
    @Excel(name = "Number of roles", cellType = ColumnType.NUMERIC)
    private Long roleId;

    /** The role name. */
    @Excel(name = "The role name.")
    private String roleName;

    /** The role authority */
    @Excel(name = "The role authority")
    private String roleKey;

    /** Role of Role */
    @Excel(name = "Role of Role")
    private Integer roleSort;

    /** scope of data（1：All data permits；2：Custom Data Authority；3：Data authority of this department；4：This department and the following data authorities；5：Only personal data authority.） */
    @Excel(name = "scope of data", readConverterExp = "1=All data permits,2=Custom Data Authority,3=Data authority of this department,4=This department and the following data authorities,5=Only personal data authority.")
    private String dataScope;

    /** Menu tree options are linked to display（ 0：Father is not connected. 1：Father is connected.） */
    private boolean menuCheckStrictly;

    /** Department Tree Options Related to Show（0：Father is not connected. 1：Father is connected. ） */
    private boolean deptCheckStrictly;

    /** Status of role.（0Normal 1stopped） */
    @Excel(name = "Status of role.", readConverterExp = "0=Normal,1=stopped")
    private String status;

    /** Remove the label.（0representations exist. 2The representative removed.） */
    private String delFlag;

    /** Does the user have this role ID? There is no presumption. */
    private boolean flag = false;

    /** The menu group */
    private Long[] menuIds;

    /** The department group（Data authority） */
    private Long[] deptIds;

    /** Role of Role Menu */
    private Set<String> permissions;

    public SysRole()
    {

    }

    public SysRole(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }

    @NotBlank(message = "The name cannot be empty.")
    @Size(min = 0, max = 30, message = "The length of the role name cannot exceed30A character.")
    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @NotBlank(message = "The permission characters cannot be empty.")
    @Size(min = 0, max = 100, message = "The length of the permission characters cannot exceed100A character.")
    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey(String roleKey)
    {
        this.roleKey = roleKey;
    }

    @NotNull(message = "Showing order cannot be empty.")
    public Integer getRoleSort()
    {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort)
    {
        this.roleSort = roleSort;
    }

    public String getDataScope()
    {
        return dataScope;
    }

    public void setDataScope(String dataScope)
    {
        this.dataScope = dataScope;
    }

    public boolean isMenuCheckStrictly()
    {
        return menuCheckStrictly;
    }

    public void setMenuCheckStrictly(boolean menuCheckStrictly)
    {
        this.menuCheckStrictly = menuCheckStrictly;
    }

    public boolean isDeptCheckStrictly()
    {
        return deptCheckStrictly;
    }

    public void setDeptCheckStrictly(boolean deptCheckStrictly)
    {
        this.deptCheckStrictly = deptCheckStrictly;
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

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public Long[] getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds)
    {
        this.menuIds = menuIds;
    }

    public Long[] getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(Long[] deptIds)
    {
        this.deptIds = deptIds;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("roleName", getRoleName())
            .append("roleKey", getRoleKey())
            .append("roleSort", getRoleSort())
            .append("dataScope", getDataScope())
            .append("menuCheckStrictly", isMenuCheckStrictly())
            .append("deptCheckStrictly", isDeptCheckStrictly())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
