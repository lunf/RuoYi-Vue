package com.ruoyi.common.utils.uuid;

import com.ruoyi.common.utils.uuid.UUID;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * ID generator tools
 * 
 * @author ruoyi
 */
public class IdUtils
{
    /**
     * Get random UUID
     * 
     * @return Random UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * Simplified UUID, without the dash line
     * 
     * @return Simplified UUID, without the dash line
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * Obtain a random UUID and use the better-performing ThreadLocalRandom to generate a UUID
     * 
     * @return Random UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * Simplified UUID, remove the dash line, use better performance ThreadLocalRandom to generate UUID
     * 
     * @return Simplified UUID, without the dash line
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }

    /**
     * Return Epoch time in String format
     * @return Epoch time
     */
    public static synchronized String fastEpochSecond() {

        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());
        builder.insert(6, "-");

        // Sleep so the next run should be different number
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        return builder.toString();
    }
}
