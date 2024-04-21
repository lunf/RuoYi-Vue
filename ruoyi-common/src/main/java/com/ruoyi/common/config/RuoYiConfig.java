package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Read the project related configuration.
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig
{
    /** Name of Project */
    private String name;

    /** The version */
    private String version;

    /** Copyright Year */
    private String copyrightYear;

    /** The Route */
    private static String profile;

    /** Get the address. */
    private static boolean addressEnabled;

    /** Type of verification code */
    private static String captchaType;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        RuoYiConfig.captchaType = captchaType;
    }

    /**
     * Get the way to import.
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * Get the image upload route.
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * Get the download route.
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * Get the upload route.
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
