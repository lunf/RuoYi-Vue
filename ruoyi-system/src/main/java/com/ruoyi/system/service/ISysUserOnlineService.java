package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.SysUserOnline;

/**
 * Online users of service.
 * 
 * @author ruoyi
 */
public interface ISysUserOnlineService
{
    /**
     * Ask for information through the login address.
     * 
     * @param ipaddr Registration Address
     * @param user User Information
     * @return Online User Information
     */
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    /**
     * Ask for information through user name
     * 
     * @param userName User Name
     * @param user User Information
     * @return Online User Information
     */
    public SysUserOnline selectOnlineByUserName(String userName, LoginUser user);

    /**
     * by login address./User Name Request Information
     * 
     * @param ipaddr Registration Address
     * @param userName User Name
     * @param user User Information
     * @return Online User Information
     */
    public SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

    /**
     * Set up online user information
     * 
     * @param user User Information
     * @return Online users
     */
    public SysUserOnline loginUserToUserOnline(LoginUser user);
}
