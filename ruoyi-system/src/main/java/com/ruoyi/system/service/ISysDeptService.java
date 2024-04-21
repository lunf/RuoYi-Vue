package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;

/**
 * Department Management of service.
 * 
 * @author ruoyi
 */
public interface ISysDeptService
{
    /**
     * Department Data Management
     * 
     * @param dept Department Information
     * @return Department Information Collection
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * Information about the tree structure department
     * 
     * @param dept Department Information
     * @return Department Tree Information Collection
     */
    public List<TreeSelect> selectDeptTreeList(SysDept dept);

    /**
     * The building of the front structure is required.
     * 
     * @param depts List of departments
     * @return List of tree structures
     */
    public List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * The building of the front end requires the drawing tree structure.
     * 
     * @param depts List of departments
     * @return List of tree structures
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * According to the roleIDInformation of the tree department
     * 
     * @param roleId The roleID
     * @return Selected department list
     */
    public List<Long> selectDeptListByRoleId(Long roleId);

    /**
     * According to the departmentIDAsk for information
     * 
     * @param deptId DepartmentID
     * @return Department Information
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * based onIDConsulting all sub-departments.（in normal state.）
     * 
     * @param deptId DepartmentID
     * @return Number of subsidiaries
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * Is there a subsection nodes?
     * 
     * @param deptId DepartmentID
     * @return Results
     */
    public boolean hasChildByDeptId(Long deptId);

    /**
     * Ask if there are users.
     * 
     * @param deptId DepartmentID
     * @return Results true existing false There is no
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * The name of the study department is unique.
     * 
     * @param dept Department Information
     * @return Results
     */
    public boolean checkDeptNameUnique(SysDept dept);

    /**
     * Does the examination department have data authority?
     * 
     * @param deptId Departmentid
     */
    public void checkDeptDataScope(Long deptId);

    /**
     * Additional Information of the Conservation Department
     * 
     * @param dept Department Information
     * @return Results
     */
    public int insertDept(SysDept dept);

    /**
     * Modification of the Conservation Department Information
     * 
     * @param dept Department Information
     * @return Results
     */
    public int updateDept(SysDept dept);

    /**
     * Delete department management information
     * 
     * @param deptId DepartmentID
     * @return Results
     */
    public int deleteDeptById(Long deptId);
}
