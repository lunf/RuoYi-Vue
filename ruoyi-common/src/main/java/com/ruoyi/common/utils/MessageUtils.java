package com.ruoyi.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.ruoyi.common.utils.spring.SpringUtils;

/**
 * obtainedi18nResource Documents
 * 
 * @author ruoyi
 */
public class MessageUtils
{
    /**
     * Based on key and parameters. Get the news. Committed tospring messageSource
     *
     * @param code The news key.
     * @param args Parameters
     * @return Acquired international translation value
     */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
