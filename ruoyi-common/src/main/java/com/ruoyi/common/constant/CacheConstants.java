package com.ruoyi.common.constant;

/**
 * and cache.key Permanent
 * 
 * @author ruoyi
 */
public class CacheConstants
{
    /**
     * Registration of Users redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * verification code redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * Management of Parameters cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * Dictionary management cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * Prevention of Weight redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * Limits of flow redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * Number of Account Password Errors redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
}
