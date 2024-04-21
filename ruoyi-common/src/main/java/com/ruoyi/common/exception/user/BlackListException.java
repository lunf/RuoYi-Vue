package com.ruoyi.common.exception.user;

/**
 * The black listIPThe unusual
 * 
 * @author ruoyi
 */
public class BlackListException extends UserException
{
    private static final long serialVersionUID = 1L;

    public BlackListException()
    {
        super("login.blocked", null);
    }
}
