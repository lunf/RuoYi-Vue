package com.ruoyi.framework.web.domain.server;

/**
 * Information related to system documents
 * 
 * @author ruoyi
 */
public class SysFile
{
    /**
     * The track track.
     */
    private String dirName;

    /**
     * Type of Plate
     */
    private String sysTypeName;

    /**
     * Type of document
     */
    private String typeName;

    /**
     * Total size
     */
    private String total;

    /**
     * The remaining size.
     */
    private String free;

    /**
     * The amount is already used.
     */
    private String used;

    /**
     * Use of Resources
     */
    private double usage;

    public String getDirName()
    {
        return dirName;
    }

    public void setDirName(String dirName)
    {
        this.dirName = dirName;
    }

    public String getSysTypeName()
    {
        return sysTypeName;
    }

    public void setSysTypeName(String sysTypeName)
    {
        this.sysTypeName = sysTypeName;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public String getFree()
    {
        return free;
    }

    public void setFree(String free)
    {
        this.free = free;
    }

    public String getUsed()
    {
        return used;
    }

    public void setUsed(String used)
    {
        this.used = used;
    }

    public double getUsage()
    {
        return usage;
    }

    public void setUsage(double usage)
    {
        this.usage = usage;
    }
}
