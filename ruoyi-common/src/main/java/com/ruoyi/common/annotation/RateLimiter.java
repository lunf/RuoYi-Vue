package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.enums.LimitType;

/**
 * The limit notes.
 * 
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter
{
    /**
     * Limits of flowkey
     */
    public String key() default CacheConstants.RATE_LIMIT_KEY;

    /**
     * time limitation.,The second unit.
     */
    public int time() default 60;

    /**
     * The limit number.
     */
    public int count() default 100;

    /**
     * Type of limitation
     */
    public LimitType limitType() default LimitType.DEFAULT;
}
