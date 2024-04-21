package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * User Table The Data Layer
 * 
 * @author ruoyi
 */
public interface SysUserMapper
{
    /**
     * List of users according to the conditions.
     * 
     * @param sysUser User Information
     * @return User information collection
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * Under the terms of page query has been accompanied by user role list
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
     * Added User Information
     * 
     * @param user User Information
     * @return Results
     */
    public int insertUser(SysUser user);

    /**
     * Modifying User Information
     * 
     * @param user User Information
     * @return Results
     */
    public int updateUser(SysUser user);

    /**
     * Change the user image.
     * 
     * @param userName User Name
     * @param avatar The head address.
     * @return Results
     */
    public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * Restore the user password.
     * 
     * @param userName User Name
     * @param password The code
     * @return Results
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

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
     * The user name is unique.
     * 
     * @param userName User Name
     * @return Results
     */
    public SysUser checkUserNameUnique(String userName);

    /**
     * The phone number is unique.
     *
     * @param phonenumber The phone number.
     * @return Results
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * ExaminationemailIs the only
     *
     * @param email User mailbox
     * @return Results
     */
    public SysUser checkEmailUnique(String email);
}
