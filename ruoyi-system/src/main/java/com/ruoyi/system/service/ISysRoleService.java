package com.ruoyi.system.service;

import java.util.List;
import java.util.Set;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.SysUserRole;

/**
 * Role of business.
 * 
 * @author ruoyi
 */
public interface ISysRoleService
{
    /**
     * Request role data according to conditions.
     * 
     * @param role Role Information
     * @return Character Data Collection Information
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * According to UsersIDList of Characters
     * 
     * @param userId UsersID
     * @return List of roles
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    /**
     * According to UsersIDQuestion of role authority.
     * 
     * @param userId UsersID
     * @return List of permissions
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * Find all the roles.
     * 
     * @return List of roles
     */
    public List<SysRole> selectRoleAll();

    /**
     * According to UsersIDGet the Character Selection Box List
     * 
     * @param userId UsersID
     * @return Choose the role.IDList of
     */
    public List<Long> selectRoleListByUserId(Long userId);

    /**
     * through the role.IDQuestion of role.
     * 
     * @param roleId The roleID
     * @return The role object information
     */
    public SysRole selectRoleById(Long roleId);

    /**
     * The role name is unique.
     * 
     * @param role Role Information
     * @return Results
     */
    public boolean checkRoleNameUnique(SysRole role);

    /**
     * Examination of the role authority is unique.
     * 
     * @param role Role Information
     * @return Results
     */
    public boolean checkRoleKeyUnique(SysRole role);

    /**
     * Test roles allow operations.
     * 
     * @param role Role Information
     */
    public void checkRoleAllowed(SysRole role);

    /**
     * Testing whether the role has data permits
     * 
     * @param roleId The roleid
     */
    public void checkRoleDataScope(Long roleId);

    /**
     * through the role.IDNumber of roles used.
     * 
     * @param roleId The roleID
     * @return Results
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * Additional storage role information
     * 
     * @param role Role Information
     * @return Results
     */
    public int insertRole(SysRole role);

    /**
     * Modifying the role information
     * 
     * @param role Role Information
     * @return Results
     */
    public int updateRole(SysRole role);

    /**
     * Change the role status.
     * 
     * @param role Role Information
     * @return Results
     */
    public int updateRoleStatus(SysRole role);

    /**
     * Modification of data authorization information
     * 
     * @param role Role Information
     * @return Results
     */
    public int authDataScope(SysRole role);

    /**
     * through the role.IDRemove the role.
     * 
     * @param roleId The roleID
     * @return Results
     */
    public int deleteRoleById(Long roleId);

    /**
     * Remove the role information.
     * 
     * @param roleIds The role needed to be removed.ID
     * @return Results
     */
    public int deleteRoleByIds(Long[] roleIds);

    /**
     * Cancel authorized user roles
     * 
     * @param userRole User and role-related information
     * @return Results
     */
    public int deleteAuthUser(SysUserRole userRole);

    /**
     * Cancellation of authorized user roles
     * 
     * @param roleId The roleID
     * @param userIds You need to cancel authorized user data.ID
     * @return Results
     */
    public int deleteAuthUsers(Long roleId, Long[] userIds);

    /**
     * Choose authorized user roles
     * 
     * @param roleId The roleID
     * @param userIds User data needed to be deleted.ID
     * @return Results
     */
    public int insertAuthUsers(Long roleId, Long[] userIds);
}
