package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom note prevents repeated submission of the form
 * 
 * @author ruoyi
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit
{
    /**
     * interval time.(ms)，Less than this time is considered to be repeated.
     */
    public int interval() default 5000;

    /**
     * suggested news
     */
    public String message() default "No repeated submission.，Please wait a little again.";
}
