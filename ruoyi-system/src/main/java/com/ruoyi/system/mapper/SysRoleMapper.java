package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysRole;

/**
 * Character Table The Data Layer
 * 
 * @author ruoyi
 */
public interface SysRoleMapper
{
    /**
     * Request role data according to conditions.
     * 
     * @param role Role Information
     * @return Character Data Collection Information
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * According to UsersIDQuestion of role.
     * 
     * @param userId UsersID
     * @return List of roles
     */
    public List<SysRole> selectRolePermissionByUserId(Long userId);

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
     * According to UsersIDQuestion of role.
     * 
     * @param userName User Name
     * @return List of roles
     */
    public List<SysRole> selectRolesByUserName(String userName);

    /**
     * The role name is unique.
     * 
     * @param roleName The role name.
     * @return Role Information
     */
    public SysRole checkRoleNameUnique(String roleName);

    /**
     * Examination of the role authority is unique.
     * 
     * @param roleKey The role authority
     * @return Role Information
     */
    public SysRole checkRoleKeyUnique(String roleKey);

    /**
     * Change the role information.
     * 
     * @param role Role Information
     * @return Results
     */
    public int updateRole(SysRole role);

    /**
     * Additional role information
     * 
     * @param role Role Information
     * @return Results
     */
    public int insertRole(SysRole role);

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
}
