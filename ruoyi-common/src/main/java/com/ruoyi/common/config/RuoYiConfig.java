package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig
{
    /** Project name */
    private String name;

    /** Version */
    private String version;

    /** Copyright year */
    private String copyrightYear;

    /** Example demo switch */
    private boolean demoEnabled;

    /** Upload path */
    private static String profile;

    /** Get address switch */
    private static boolean addressEnabled;

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

    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        this.demoEnabled = demoEnabled;
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

    /**
     * Get avatar upload path
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * Get download path
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * Get upload path
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
