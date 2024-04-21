package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.SysUserRole;

/**
 * User and Character Relationship The Data Layer
 * 
 * @author ruoyi
 */
public interface SysUserRoleMapper
{
    /**
     * through the userIDRemove user and role connections
     * 
     * @param userId UsersID
     * @return Results
     */
    public int deleteUserRoleByUserId(Long userId);

    /**
     * Mass removal of users and roles related
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteUserRole(Long[] ids);

    /**
     * through the role.IDNumber of roles used.
     * 
     * @param roleId The roleID
     * @return Results
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * Adding new user role information
     * 
     * @param userRoleList User Character List
     * @return Results
     */
    public int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * Delete user and role-related information
     * 
     * @param userRole User and role-related information
     * @return Results
     */
    public int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * Cancellation of authorized user roles
     * 
     * @param roleId The roleID
     * @param userIds User data needed to be deleted.ID
     * @return Results
     */
    public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}
