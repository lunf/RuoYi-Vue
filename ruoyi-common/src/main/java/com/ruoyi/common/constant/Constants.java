package com.ruoyi.common.constant;

import java.util.Locale;
import io.jsonwebtoken.Claims;

/**
 * Permanent information
 * 
 * @author ruoyi
 */
public class Constants
{
    /**
     * UTF-8 Collection of characters
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK Collection of characters
     */
    public static final String GBK = "GBK";

    /**
     * system language
     */
    public static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    /**
     * wwwThe main area
     */
    public static final String WWW = "www.";

    /**
     * httprequested
     */
    public static final String HTTP = "http://";

    /**
     * httpsrequested
     */
    public static final String HTTPS = "https://";

    /**
     * General success mark.
     */
    public static final String SUCCESS = "0";

    /**
     * General Failure Signs
     */
    public static final String FAIL = "1";

    /**
     * Register Successful
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * Cancelled
     */
    public static final String LOGOUT = "Logout";

    /**
     * Registered
     */
    public static final String REGISTER = "Register";

    /**
     * Registration failed.
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * Identify all powers.
     */
    public static final String ALL_PERMISSION = "*:*:*";

    /**
     * Identification of administrator roles.
     */
    public static final String SUPER_ADMIN = "admin";

    /**
     * The role authority separator.
     */
    public static final String ROLE_DELIMETER = ",";

    /**
     * Identifying the separator.
     */
    public static final String PERMISSION_DELIMETER = ",";

    /**
     * Verification code validity.（Minutes）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * The mark.
     */
    public static final String TOKEN = "token";

    /**
     * Signs in front.
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Signs in front.
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * UsersID
     */
    public static final String JWT_USERID = "userid";

    /**
     * User Name
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * User image
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * Creating time.
     */
    public static final String JWT_CREATED = "created";

    /**
     * User permits
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * Routes of Resources The Preview
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI Remote method call
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP Remote method call
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS Remote method call
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * Automatic IdentificationjsonThe White List of Objects（Only permitted analysis.，The smaller the safer.）
     */
    public static final String[] JSON_WHITELIST_STR = { "org.springframework", "com.ruoyi" };

    /**
     * Configuration of timely task list（Only permitted access.，Other needs can be added on your own.）
     */
    public static final String[] JOB_WHITELIST_STR = { "com.ruoyi.quartz.task" };

    /**
     * Temporary tasks of irregularity.
     */
    public static final String[] JOB_ERROR_STR = { "java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.ruoyi.common.utils.file", "com.ruoyi.common.config", "com.ruoyi.generator" };
}
