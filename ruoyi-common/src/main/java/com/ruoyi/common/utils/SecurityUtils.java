package com.ruoyi.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.PatternMatchUtils;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;

/**
 * Secure Service Tools
 * 
 * @author ruoyi
 */
public class SecurityUtils
{

    /**
     * UsersID
     **/
    public static Long getUserId()
    {
        try
        {
            return getLoginUser().getUserId();
        }
        catch (Exception e)
        {
            throw new ServiceException("Obtaining UsersIDUnusual", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Acquired departmentID
     **/
    public static Long getDeptId()
    {
        try
        {
            return getLoginUser().getDeptId();
        }
        catch (Exception e)
        {
            throw new ServiceException("Acquired departmentIDUnusual", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Obtaining User Account
     **/
    public static String getUsername()
    {
        try
        {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            throw new ServiceException("Unusual access to user accounts", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Obtaining Users
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new ServiceException("Unusual access to user information.", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * obtainedAuthentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * producedBCryptPasswordEncoderThe code
     *
     * @param password The code
     * @return Encryption of characters
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * Find out if the password is the same.
     *
     * @param rawPassword The real code.
     * @param encodedPassword Postcrypted characters
     * @return Results
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * to the manager.
     * 
     * @param userId UsersID
     * @return Results
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    /**
     * Verify whether the user has any authorization.
     * 
     * @param permission Authorization of characters
     * @return Does the user have any permission?
     */
    public static boolean hasPermi(String permission)
    {
        return hasPermi(getLoginUser().getPermissions(), permission);
    }

    /**
     * Deciding whether it contains authority.
     * 
     * @param authorities List of permissions
     * @param permission Authorization of characters
     * @return Does the user have any permission?
     */
    public static boolean hasPermi(Collection<String> authorities, String permission)
    {
        return authorities.stream().filter(StringUtils::hasText)
                .anyMatch(x -> Constants.ALL_PERMISSION.equals(x) || PatternMatchUtils.simpleMatch(x, permission));
    }

    /**
     * Verify whether the user has a role.
     * 
     * @param role Character Identification
     * @return Does the user have a role?
     */
    public static boolean hasRole(String role)
    {
        List<SysRole> roleList = getLoginUser().getUser().getRoles();
        Collection<String> roles = roleList.stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
        return hasRole(roles, role);
    }

    /**
     * Deciding whether it includes a role.
     * 
     * @param roles List of roles
     * @param role The role
     * @return Does the user have a role authorization?
     */
    public static boolean hasRole(Collection<String> roles, String role)
    {
        return roles.stream().filter(StringUtils::hasText)
                .anyMatch(x -> Constants.SUPER_ADMIN.equals(x) || PatternMatchUtils.simpleMatch(x, role));
    }

}
