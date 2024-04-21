package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.OperatorType;

/**
 * Custom operating log record notes
 * 
 * @author ruoyi
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * The Module
     */
    public String title() default "";

    /**
     * functional
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * Operate humanity.
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * Save the request parameters.
     */
    public boolean isSaveRequestData() default true;

    /**
     * How to save the response parameters.
     */
    public boolean isSaveResponseData() default true;

    /**
     * Excluding the specified request parameters
     */
    public String[] excludeParamNames() default {};
}
