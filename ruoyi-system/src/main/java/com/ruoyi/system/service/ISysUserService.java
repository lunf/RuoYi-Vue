package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * Users The business level.
 * 
 * @author ruoyi
 */
public interface ISysUserService
{
    /**
     * List of users according to the conditions.
     * 
     * @param user User Information
     * @return User information collection
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * Under the terms of page query has been allocated user role list
     * 
     * @param user User Information
     * @return User information collection
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * Under the terms of page query not allocated user role lists
     * 
     * @param user User Information
     * @return User information collection
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * Ask Users through Username
     * 
     * @param userName User Name
     * @return User Objects Information
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * through the userIDAsk the user.
     * 
     * @param userId UsersID
     * @return User Objects Information
     */
    public SysUser selectUserById(Long userId);

    /**
     * According to UsersIDAsk for the role group of users.
     * 
     * @param userName User Name
     * @return Results
     */
    public String selectUserRoleGroup(String userName);

    /**
     * According to UsersIDAsk for the employment group of users.
     * 
     * @param userName User Name
     * @return Results
     */
    public String selectUserPostGroup(String userName);

    /**
     * The user name is unique.
     * 
     * @param user User Information
     * @return Results
     */
    public boolean checkUserNameUnique(SysUser user);

    /**
     * The phone number is unique.
     *
     * @param user User Information
     * @return Results
     */
    public boolean checkPhoneUnique(SysUser user);

    /**
     * ExaminationemailIs the only
     *
     * @param user User Information
     * @return Results
     */
    public boolean checkEmailUnique(SysUser user);

    /**
     * Testing whether the user allows operations
     * 
     * @param user User Information
     */
    public void checkUserAllowed(SysUser user);

    /**
     * Verify whether the user has data permits
     * 
     * @param userId Usersid
     */
    public void checkUserDataScope(Long userId);

    /**
     * Added User Information
     * 
     * @param user User Information
     * @return Results
     */
    public int insertUser(SysUser user);

    /**
     * Registration of User Information
     * 
     * @param user User Information
     * @return Results
     */
    public boolean registerUser(SysUser user);

    /**
     * Modifying User Information
     * 
     * @param user User Information
     * @return Results
     */
    public int updateUser(SysUser user);

    /**
     * User authorized role
     * 
     * @param userId UsersID
     * @param roleIds The role group
     */
    public void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * Modification of User Status
     * 
     * @param user User Information
     * @return Results
     */
    public int updateUserStatus(SysUser user);

    /**
     * Modifying basic user information
     * 
     * @param user User Information
     * @return Results
     */
    public int updateUserProfile(SysUser user);

    /**
     * Change the user image.
     * 
     * @param userName User Name
     * @param avatar The head address.
     * @return Results
     */
    public boolean updateUserAvatar(String userName, String avatar);

    /**
     * Restore the user password.
     * 
     * @param user User Information
     * @return Results
     */
    public int resetPwd(SysUser user);

    /**
     * Restore the user password.
     * 
     * @param userName User Name
     * @param password The code
     * @return Results
     */
    public int resetUserPwd(String userName, String password);

    /**
     * through the userIDRemove Users
     * 
     * @param userId UsersID
     * @return Results
     */
    public int deleteUserById(Long userId);

    /**
     * Remove User Information
     * 
     * @param userIds Users to be removed.ID
     * @return Results
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * Import User Data
     * 
     * @param userList User Data List
     * @param isUpdateSupport Updated support，If there is already，Update the data.
     * @param operName Operating Users
     * @return Results
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);
}
