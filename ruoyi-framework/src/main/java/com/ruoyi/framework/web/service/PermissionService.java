package com.ruoyi.framework.web.service;

import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.context.PermissionContextHolder;

/**
 * RuoYiThe first Implementation of custom authority，sstaken fromSpringSecurityThe first letter.
 * 
 * @author ruoyi
 */
@Service("ss")
public class PermissionService
{
    /**
     * Verify whether the user has any authorization.
     * 
     * @param permission Authorization of characters
     * @return Does the user have any permission?
     */
    public boolean hasPermi(String permission)
    {
        if (StringUtils.isEmpty(permission))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        PermissionContextHolder.setContext(permission);
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * Verify whether the user has no authorization.，and hasPermiLogic is the opposite.
     *
     * @param permission Authorization of characters
     * @return Does the user have no permission?
     */
    public boolean lacksPermi(String permission)
    {
        return hasPermi(permission) != true;
    }

    /**
     * Verify whether the user has the following arbitrary authorization:
     *
     * @param permissions by PERMISSION_DELIMETER Authorization list for separators
     * @return Does the user have the following arbitrary authorization?
     */
    public boolean hasAnyPermi(String permissions)
    {
        if (StringUtils.isEmpty(permissions))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        PermissionContextHolder.setContext(permissions);
        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(Constants.PERMISSION_DELIMETER))
        {
            if (permission != null && hasPermissions(authorities, permission))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * To determine whether the user has a role.
     * 
     * @param role The character string.
     * @return Does the user have a role?
     */
    public boolean hasRole(String role)
    {
        if (StringUtils.isEmpty(role))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
        {
            return false;
        }
        for (SysRole sysRole : loginUser.getUser().getRoles())
        {
            String roleKey = sysRole.getRoleKey();
            if (Constants.SUPER_ADMIN.equals(roleKey) || roleKey.equals(StringUtils.trim(role)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify whether the user has no role.，and isRoleLogic is the opposite.。
     *
     * @param role The role name.
     * @return Does the user have no role?
     */
    public boolean lacksRole(String role)
    {
        return hasRole(role) != true;
    }

    /**
     * Verify whether the user has the following arbitrary role:
     *
     * @param roles by ROLE_NAMES_DELIMETER List of roles for separators
     * @return Does the user have the following arbitrary role?
     */
    public boolean hasAnyRoles(String roles)
    {
        if (StringUtils.isEmpty(roles))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
        {
            return false;
        }
        for (String role : roles.split(Constants.ROLE_DELIMETER))
        {
            if (hasRole(role))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Deciding whether it contains authority.
     * 
     * @param permissions List of permissions
     * @param permission Authorization of characters
     * @return Does the user have any permission?
     */
    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(Constants.ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }
}
