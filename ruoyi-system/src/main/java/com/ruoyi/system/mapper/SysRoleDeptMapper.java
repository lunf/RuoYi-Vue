package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysRoleDept;

/**
 * Role and Department Relationship The Data Layer
 * 
 * @author ruoyi
 */
public interface SysRoleDeptMapper
{
    /**
     * through the role.IDRemove roles and department connections
     * 
     * @param roleId The roleID
     * @return Results
     */
    public int deleteRoleDeptByRoleId(Long roleId);

    /**
     * Remove the role department related information
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteRoleDept(Long[] ids);

    /**
     * Number of use by the survey department
     * 
     * @param deptId DepartmentID
     * @return Results
     */
    public int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * Additional role department information
     * 
     * @param roleDeptList List of role departments
     * @return Results
     */
    public int batchRoleDept(List<SysRoleDept> roleDeptList);
}
