package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.ruoyi.common.enums.DataSourceType;

/**
 * Customized Multi-Data Source Exchange Notes
 *
 * Priority：The first method，after class，If the method covers the data source type in the class，according to the method.，Otherwise in class.
 *
 * @author ruoyi
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    /**
     * Change the data source name.
     */
    public DataSourceType value() default DataSourceType.MASTER;
}
