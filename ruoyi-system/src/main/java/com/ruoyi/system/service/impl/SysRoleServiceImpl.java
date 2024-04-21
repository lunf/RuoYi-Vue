package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysRoleDept;
import com.ruoyi.system.domain.SysRoleMenu;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysRoleDeptMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysRoleMenuMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysRoleService;

/**
 * The role Processing of business level
 * 
 * @author ruoyi
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * Request role data according to conditions.
     * 
     * @param role Role Information
     * @return Character Data Collection Information
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * According to UsersIDQuestion of role.
     * 
     * @param userId UsersID
     * @return List of roles
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId)
    {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * According to UsersIDRequest authority
     * 
     * @param userId UsersID
     * @return List of permissions
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * Find all the roles.
     * 
     * @return List of roles
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * According to UsersIDGet the Character Selection Box List
     * 
     * @param userId UsersID
     * @return Choose the role.IDList of
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * through the role.IDQuestion of role.
     * 
     * @param roleId The roleID
     * @return The role object information
     */
    @Override
    public SysRole selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * The role name is unique.
     * 
     * @param role Role Information
     * @return Results
     */
    @Override
    public boolean checkRoleNameUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Examination of the role authority is unique.
     * 
     * @param role Role Information
     * @return Results
     */
    @Override
    public boolean checkRoleKeyUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Test roles allow operations.
     * 
     * @param role Role Information
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new ServiceException("It is not allowed to operate supermanager roles.");
        }
    }

    /**
     * Testing whether the role has data permits
     * 
     * @param roleId The roleid
     */
    @Override
    public void checkRoleDataScope(Long roleId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysRole role = new SysRole();
            role.setRoleId(roleId);
            List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
            if (StringUtils.isEmpty(roles))
            {
                throw new ServiceException("No access to role data.！");
            }
        }
    }

    /**
     * through the role.IDNumber of roles used.
     * 
     * @param roleId The roleID
     * @return Results
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * Additional storage role information
     * 
     * @param role Role Information
     * @return Results
     */
    @Override
    @Transactional
    public int insertRole(SysRole role)
    {
        // Additional role information
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * Modifying the role information
     * 
     * @param role Role Information
     * @return Results
     */
    @Override
    @Transactional
    public int updateRole(SysRole role)
    {
        // Change the role information.
        roleMapper.updateRole(role);
        // Remove Character Related to Menu
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * Change the role status.
     * 
     * @param role Role Information
     * @return Results
     */
    @Override
    public int updateRoleStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * Modification of data authorization information
     * 
     * @param role Role Information
     * @return Results
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role)
    {
        // Change the role information.
        roleMapper.updateRole(role);
        // Remove the role related to the department
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // Additional role and department information（Data authority）
        return insertRoleDept(role);
    }

    /**
     * Add new role menu information
     * 
     * @param role The role objects
     */
    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;
        // Adding users and role management
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * Additional role department information(Data authority)
     *
     * @param role The role objects
     */
    public int insertRoleDept(SysRole role)
    {
        int rows = 1;
        // Added roles and departments（Data authority）management
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * through the role.IDRemove the role.
     * 
     * @param roleId The roleID
     * @return Results
     */
    @Override
    @Transactional
    public int deleteRoleById(Long roleId)
    {
        // Remove Character Related to Menu
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // Remove the role related to the department
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * Remove the role information.
     * 
     * @param roleIds The role needed to be removed.ID
     * @return Results
     */
    @Override
    @Transactional
    public int deleteRoleByIds(Long[] roleIds)
    {
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new ServiceException(String.format("%1$shas been distributed.,cannot be removed.", role.getRoleName()));
            }
        }
        // Remove Character Related to Menu
        roleMenuMapper.deleteRoleMenu(roleIds);
        // Remove the role related to the department
        roleDeptMapper.deleteRoleDept(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * Cancel authorized user roles
     * 
     * @param userRole User and role-related information
     * @return Results
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole)
    {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * Cancellation of authorized user roles
     * 
     * @param roleId The roleID
     * @param userIds You need to cancel authorized user data.ID
     * @return Results
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds)
    {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * Choose authorized user roles
     * 
     * @param roleId The roleID
     * @param userIds Required authorized user dataID
     * @return Results
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds)
    {
        // Adding users and role management
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Long userId : userIds)
        {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}
