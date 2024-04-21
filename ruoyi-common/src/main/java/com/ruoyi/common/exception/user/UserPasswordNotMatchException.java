package com.ruoyi.common.exception.user;

/**
 * User password is incorrect or incompatible with the standard irregularity.
 * 
 * @author ruoyi
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("user.password.not.match", null);
    }
}
