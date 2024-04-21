package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysDept;

/**
 * Department Management The Data Layer
 * 
 * @author ruoyi
 */
public interface SysDeptMapper
{
    /**
     * Department Data Management
     * 
     * @param dept Department Information
     * @return Department Information Collection
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * According to the roleIDInformation of the tree department
     * 
     * @param roleId The roleID
     * @param deptCheckStrictly Department Tree Options Related to Show
     * @return Selected department list
     */
    public List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * According to the departmentIDAsk for information
     * 
     * @param deptId DepartmentID
     * @return Department Information
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * based onIDConsulting all sub-departments.
     * 
     * @param deptId DepartmentID
     * @return List of departments
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * based onIDConsulting all sub-departments.（in normal state.）
     * 
     * @param deptId DepartmentID
     * @return Number of subsidiaries
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * Is there a subnode?
     * 
     * @param deptId DepartmentID
     * @return Results
     */
    public int hasChildByDeptId(Long deptId);

    /**
     * Ask if there are users.
     * 
     * @param deptId DepartmentID
     * @return Results
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * The name of the study department is unique.
     * 
     * @param deptName Name of department
     * @param parentId The Father DepartmentID
     * @return Results
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * Additional Department Information
     * 
     * @param dept Department Information
     * @return Results
     */
    public int insertDept(SysDept dept);

    /**
     * Modification of Department Information
     * 
     * @param dept Department Information
     * @return Results
     */
    public int updateDept(SysDept dept);

    /**
     * Modifying the normal state of the department.
     * 
     * @param deptIds DepartmentIDThe group
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * Modification of Element Relationship
     * 
     * @param depts The element.
     * @return Results
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * Delete department management information
     * 
     * @param deptId DepartmentID
     * @return Results
     */
    public int deleteDeptById(Long deptId);
}
