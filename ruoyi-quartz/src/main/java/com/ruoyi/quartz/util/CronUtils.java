package com.ruoyi.quartz.util;

import java.text.ParseException;
import java.util.Date;
import org.quartz.CronExpression;

/**
 * cronExpression Tools
 * 
 * @author ruoyi
 *
 */
public class CronUtils
{
    /**
     * Return a bull value represents a givenCronEffectiveness of expression
     *
     * @param cronExpression CronThe expression
     * @return boolean Is the expression effective?
     */
    public static boolean isValid(String cronExpression)
    {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * Return a string value.,The news is ineffective.CronExpression is effective.
     *
     * @param cronExpression CronThe expression
     * @return String Return the expression error description when invalid,If effective return.null
     */
    public static String getInvalidMessage(String cronExpression)
    {
        try
        {
            new CronExpression(cronExpression);
            return null;
        }
        catch (ParseException pe)
        {
            return pe.getMessage();
        }
    }

    /**
     * Return to the next execution time according to the givenCronThe expression
     *
     * @param cronExpression CronThe expression
     * @return Date The next timeCronExpression of time.
     */
    public static Date getNextExecution(String cronExpression)
    {
        try
        {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
