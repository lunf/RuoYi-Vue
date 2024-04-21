package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysPostMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserPostMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

/**
 * Users Processing of business level
 * 
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    /**
     * List of users according to the conditions.
     * 
     * @param user User Information
     * @return User information collection
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * Under the terms of page query has been allocated user role list
     * 
     * @param user User Information
     * @return User information collection
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * Under the terms of page query not allocated user role lists
     * 
     * @param user User Information
     * @return User information collection
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * Ask Users through Username
     * 
     * @param userName User Name
     * @return User Objects Information
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * through the userIDAsk the user.
     * 
     * @param userId UsersID
     * @return User Objects Information
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * Ask for the role group of users.
     * 
     * @param userName User Name
     * @return Results
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * Ask for the employment group of users.
     * 
     * @param userName User Name
     * @return Results
     */
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * The user name is unique.
     * 
     * @param user User Information
     * @return Results
     */
    @Override
    public boolean checkUserNameUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * The phone number is unique.
     *
     * @param user User Information
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * ExaminationemailIs the only
     *
     * @param user User Information
     * @return
     */
    @Override
    public boolean checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Testing whether the user allows operations
     * 
     * @param user User Information
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("Do not allow Super Administrator users to operate");
        }
    }

    /**
     * Verify whether the user has data permits
     * 
     * @param userId Usersid
     */
    @Override
    public void checkUserDataScope(Long userId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("No access to user data.！");
            }
        }
    }

    /**
     * Add to storing user information
     * 
     * @param user User Information
     * @return Results
     */
    @Override
    @Transactional
    public int insertUser(SysUser user)
    {
        // Added User Information
        int rows = userMapper.insertUser(user);
        // Additional User Jobs Related
        insertUserPost(user);
        // Adding users and role management
        insertUserRole(user);
        return rows;
    }

    /**
     * Registration of User Information
     * 
     * @param user User Information
     * @return Results
     */
    @Override
    public boolean registerUser(SysUser user)
    {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * Modification of User Information
     * 
     * @param user User Information
     * @return Results
     */
    @Override
    @Transactional
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        // Remove User Relationships with Characters
        userRoleMapper.deleteUserRoleByUserId(userId);
        // Adding users and role management
        insertUserRole(user);
        // Remove User Relationships with Jobs
        userPostMapper.deleteUserPostByUserId(userId);
        // Adding users and job management
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * User authorized role
     * 
     * @param userId UsersID
     * @param roleIds The role group
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * Modification of User Status
     * 
     * @param user User Information
     * @return Results
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * Modifying basic user information
     * 
     * @param user User Information
     * @return Results
     */
    @Override
    public int updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * Change the user image.
     * 
     * @param userName User Name
     * @param avatar The head address.
     * @return Results
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * Restore the user password.
     * 
     * @param user User Information
     * @return Results
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * Restore the user password.
     * 
     * @param userName User Name
     * @param password The code
     * @return Results
     */
    @Override
    public int resetUserPwd(String userName, String password)
    {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * Adding User Role Information
     * 
     * @param user User Objects
     */
    public void insertUserRole(SysUser user)
    {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    /**
     * Additional User Job Information
     * 
     * @param user User Objects
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotEmpty(posts))
        {
            // Adding users and job management
            List<SysUserPost> list = new ArrayList<SysUserPost>(posts.length);
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * Adding User Role Information
     * 
     * @param userId UsersID
     * @param roleIds The role group
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotEmpty(roleIds))
        {
            // Adding users and role management
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * through the userIDRemove Users
     * 
     * @param userId UsersID
     * @return Results
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId)
    {
        // Remove User Relationships with Characters
        userRoleMapper.deleteUserRoleByUserId(userId);
        // Remove users and job tables
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * Remove User Information
     * 
     * @param userIds Users to be removed.ID
     * @return Results
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // Remove User Relationships with Characters
        userRoleMapper.deleteUserRole(userIds);
        // Remove User Relationships with Jobs
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * Import User Data
     * 
     * @param userList User Data List
     * @param isUpdateSupport Updated support，If there is already，Update the data.
     * @param operName Operating Users
     * @return Results
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("User data cannot be empty.！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // Verify whether this user exists.
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    userMapper.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、The Account " + user.getUserName() + " Introduction to Success");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(u);
                    checkUserDataScope(u.getUserId());
                    user.setUserId(u.getUserId());
                    user.setUpdateBy(operName);
                    userMapper.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、The Account " + user.getUserName() + " Updated success");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、The Account " + user.getUserName() + " has existed.");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、The Account " + user.getUserName() + " Introduction Failure：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "Sorry to me.，Introduction Failure！of " + failureNum + " The data format is incorrect.，The mistake is as follows.：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "Congratulations to you，All data has been successfully imported.！of " + successNum + " of，The data is as follows：");
        }
        return successMsg.toString();
    }
}
