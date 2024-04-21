package com.ruoyi.common.exception.user;

/**
 * Unusual verification code failure
 * 
 * @author ruoyi
 */
public class CaptchaExpireException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super("user.jcaptcha.expire", null);
    }
}
