package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Data permits filter notes
 * 
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope
{
    /**
     * Other names of the department table
     */
    public String deptAlias() default "";

    /**
     * Name of the User Table.
     */
    public String userAlias() default "";

    /**
     * Authorization of characters（Use multiple roles to match the requirements）Based on authorization.@ssobtained，Multiple powers are separated by comics.
     */
    public String permission() default "";
}
