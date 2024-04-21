package com.ruoyi.common.utils.sql;

import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.StringUtils;

/**
 * sqlOperating tools
 * 
 * @author ruoyi
 */
public class SqlUtil
{
    /**
     * Definition of common use sqlKeywords
     */
    public static String SQL_REGEX = "and |extractvalue|updatexml|exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |or |+|user()";

    /**
     * Support only letters.、The numbers、The line down.、The Space、The Comedy、small numbers.（Support multiple fields.）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * The limitationorderByThe maximum length.
     */
    private static final int ORDER_BY_MAX_LENGTH = 500;

    /**
     * Check the characters.，Prevent the injection.
     */
    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            throw new UtilException("Parameters do not comply with the standards.，cannot be investigated.");
        }
        if (StringUtils.length(value) > ORDER_BY_MAX_LENGTH)
        {
            throw new UtilException("Parameters have exceeded the maximum limit.，cannot be investigated.");
        }
        return value;
    }

    /**
     * Verified order by Is the language in accordance with the standards?
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQLKeyword checking
     */
    public static void filterKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeyword) > -1)
            {
                throw new UtilException("Parameters exist.SQLInjection of risk.");
            }
        }
    }
}
