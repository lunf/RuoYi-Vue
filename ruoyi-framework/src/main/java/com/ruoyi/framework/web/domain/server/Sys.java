package com.ruoyi.framework.web.domain.server;

/**
 * Information related to the system
 * 
 * @author ruoyi
 */
public class Sys
{
    /**
     * Name of the server
     */
    private String computerName;

    /**
     * The server.Ip
     */
    private String computerIp;

    /**
     * The project route
     */
    private String userDir;

    /**
     * Operating system
     */
    private String osName;

    /**
     * The system architecture
     */
    private String osArch;

    public String getComputerName()
    {
        return computerName;
    }

    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

    public String getComputerIp()
    {
        return computerIp;
    }

    public void setComputerIp(String computerIp)
    {
        this.computerIp = computerIp;
    }

    public String getUserDir()
    {
        return userDir;
    }

    public void setUserDir(String userDir)
    {
        this.userDir = userDir;
    }

    public String getOsName()
    {
        return osName;
    }

    public void setOsName(String osName)
    {
        this.osName = osName;
    }

    public String getOsArch()
    {
        return osArch;
    }

    public void setOsArch(String osArch)
    {
        this.osArch = osArch;
    }
}
