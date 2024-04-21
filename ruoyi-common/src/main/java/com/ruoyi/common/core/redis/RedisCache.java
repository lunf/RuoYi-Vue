package com.ruoyi.common.core.redis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * spring redis Class of tools
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache
{
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * Cache the basic objects.，Integer、String、Essentials and so.
     *
     * @param key Key value of cache.
     * @param value The value of cache
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Cache the basic objects.，Integer、String、Essentials and so.
     *
     * @param key Key value of cache.
     * @param value The value of cache
     * @param timeout time
     * @param timeUnit Time of particles.
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Set up effective time.
     *
     * @param key RedisKey to
     * @param timeout Overtime time
     * @return true=Setup Success；false=Set for Failure.
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Set up effective time.
     *
     * @param key RedisKey to
     * @param timeout Overtime time
     * @param unit time unit
     * @return true=Setup Success；false=Set for Failure.
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Get effective time.
     *
     * @param key RedisKey to
     * @return effective time.
     */
    public long getExpire(final String key)
    {
        return redisTemplate.getExpire(key);
    }

    /**
     * judgment keyIs there existing
     *
     * @param key Key to
     * @return true existing falseThere is no
     */
    public Boolean hasKey(String key)
    {
        return redisTemplate.hasKey(key);
    }

    /**
     * Basic Objects of Cache。
     *
     * @param key The key value.
     * @return Data corresponding key value.
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * Remove individual objects.
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * Remove the collective object.
     *
     * @param collection multiple objects.
     * @return
     */
    public boolean deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * cacheListThe data
     *
     * @param key Key value of cache.
     * @param dataList to be stored.ListThe data
     * @return Objects of Cache
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * obtained cache.listObjects
     *
     * @param key Key value of cache.
     * @return Data corresponding key value.
     */
    public <T> List<T> getCacheList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * cacheSet
     *
     * @param key The key value.
     * @param dataSet Cache data
     * @return Objects of data cache
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * obtained cache.set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * cacheMap
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * obtained cache.Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * toHashInto the data.
     *
     * @param key RedisKey to
     * @param hKey HashKey to
     * @param value Value
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * obtainedHashIn the data
     *
     * @param key RedisKey to
     * @param hKey HashKey to
     * @return HashObjects in
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * Get a lot.HashIn the data
     *
     * @param key RedisKey to
     * @param hKeys HashThe key collection.
     * @return HashAssembly of objects
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * removedHashSome of the data.
     *
     * @param key RedisKey to
     * @param hKey HashKey to
     * @return is successful.
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * List of basic objects available for cache
     *
     * @param pattern The front line.
     * @return List of objects
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }
}
