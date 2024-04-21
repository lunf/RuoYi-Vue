package com.ruoyi.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data source exchange processing
 * 
 * @author ruoyi
 */
public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * Use ofThreadLocalMaintenance of Variants，ThreadLocalProvided a separate copy of the variable for each line that uses this variable，
     * So each line can independently change its own copy.，Not to affect the corresponding copies of other lines.。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * Set the data source variables.
     */
    public static void setDataSourceType(String dsType)
    {
        log.info("Change to{}Source of data", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * Access to data sources
     */
    public static String getDataSourceType()
    {
        return CONTEXT_HOLDER.get();
    }

    /**
     * The data source variable
     */
    public static void clearDataSourceType()
    {
        CONTEXT_HOLDER.remove();
    }
}
