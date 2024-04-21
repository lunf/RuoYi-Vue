package com.ruoyi.quartz.task;

import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

/**
 * Temporary task test.
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("Implement Multiple Methods： Type of string{}，Type of Bull{}，The whole type.{}，Floating Type{}，The form{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("Implementation has a method.：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("Implementing no method.");
    }
}
