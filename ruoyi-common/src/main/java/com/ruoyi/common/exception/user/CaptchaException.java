package com.ruoyi.common.exception.user;

/**
 * Unusual error verification.
 * 
 * @author ruoyi
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.jcaptcha.error", null);
    }
}
