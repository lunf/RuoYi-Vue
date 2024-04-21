package com.ruoyi.common.utils.uuid;

/**
 * IDGenerator Tools
 * 
 * @author ruoyi
 */
public class IdUtils
{
    /**
     * getting random.UUID
     * 
     * @return randomlyUUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * simplifiedUUID，Remove the horizon.
     * 
     * @return simplifiedUUID，Remove the horizon.
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * getting random.UUID，Use better performance.ThreadLocalRandomproducedUUID
     * 
     * @return randomlyUUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * simplifiedUUID，Remove the horizon.，Use better performance.ThreadLocalRandomproducedUUID
     * 
     * @return simplifiedUUID，Remove the horizon.
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}
