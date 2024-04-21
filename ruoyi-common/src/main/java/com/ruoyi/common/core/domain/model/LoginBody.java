package com.ruoyi.common.core.domain.model;

/**
 * User Login Objects
 * 
 * @author ruoyi
 */
public class LoginBody
{
    /**
     * User Name
     */
    private String username;

    /**
     * User Password
     */
    private String password;

    /**
     * verification code
     */
    private String code;

    /**
     * The only mark.
     */
    private String uuid;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
