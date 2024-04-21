package com.ruoyi.common.utils;

import java.util.Collection;
import java.util.List;
import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;

/**
 * Dictionary tools
 * 
 * @author ruoyi
 */
public class DictUtils
{
    /**
     * Separation
     */
    public static final String SEPARATOR = ",";

    /**
     * Set the dictionary cache.
     * 
     * @param key The parameter key.
     * @param dictDatas Dictionary data list
     */
    public static void setDictCache(String key, List<SysDictData> dictDatas)
    {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * Get the dictionary cache.
     * 
     * @param key The parameter key.
     * @return dictDatas Dictionary data list
     */
    public static List<SysDictData> getDictCache(String key)
    {
        JSONArray arrayCache = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(arrayCache))
        {
            return arrayCache.toList(SysDictData.class);
        }
        return null;
    }

    /**
     * Obtaining dictionary labels according to dictionary type and value
     * 
     * @param dictType Type of dictionary
     * @param dictValue The dictionary value.
     * @return dictionary labels
     */
    public static String getDictLabel(String dictType, String dictValue)
    {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * To obtain dictionary value according to dictionary type and dictionary label
     * 
     * @param dictType Type of dictionary
     * @param dictLabel dictionary labels
     * @return The dictionary value.
     */
    public static String getDictValue(String dictType, String dictLabel)
    {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * Obtaining dictionary labels according to dictionary type and value
     * 
     * @param dictType Type of dictionary
     * @param dictValue The dictionary value.
     * @param separator Separation
     * @return dictionary labels
     */
    public static String getDictLabel(String dictType, String dictValue, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);

        if (StringUtils.isNotNull(datas))
        {
            if (StringUtils.containsAny(separator, dictValue))
            {
                for (SysDictData dict : datas)
                {
                    for (String value : dictValue.split(separator))
                    {
                        if (value.equals(dict.getDictValue()))
                        {
                            propertyString.append(dict.getDictLabel()).append(separator);
                            break;
                        }
                    }
                }
            }
            else
            {
                for (SysDictData dict : datas)
                {
                    if (dictValue.equals(dict.getDictValue()))
                    {
                        return dict.getDictLabel();
                    }
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * To obtain dictionary value according to dictionary type and dictionary label
     * 
     * @param dictType Type of dictionary
     * @param dictLabel dictionary labels
     * @param separator Separation
     * @return The dictionary value.
     */
    public static String getDictValue(String dictType, String dictLabel, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas))
        {
            for (SysDictData dict : datas)
            {
                for (String label : dictLabel.split(separator))
                {
                    if (label.equals(dict.getDictLabel()))
                    {
                        propertyString.append(dict.getDictValue()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (SysDictData dict : datas)
            {
                if (dictLabel.equals(dict.getDictLabel()))
                {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * Remove the specified dictionary cache
     * 
     * @param key The dictionary key.
     */
    public static void removeDictCache(String key)
    {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(key));
    }

    /**
     * Clean dictionary cache.
     */
    public static void clearDictCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(CacheConstants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * set upcache key
     * 
     * @param configKey The parameter key.
     * @return Cache keykey
     */
    public static String getCacheKey(String configKey)
    {
        return CacheConstants.SYS_DICT_KEY + configKey;
    }
}
