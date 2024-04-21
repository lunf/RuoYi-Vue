package com.ruoyi.common.core.domain;

import java.util.HashMap;
import java.util.Objects;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.StringUtils;

/**
 * Operating Message Reminders
 * 
 * @author ruoyi
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** state code */
    public static final String CODE_TAG = "code";

    /** Return to Content */
    public static final String MSG_TAG = "msg";

    /** Data Objects */
    public static final String DATA_TAG = "data";

    /**
     * Starting a new creation. AjaxResult Objects，Give it an empty message.。
     */
    public AjaxResult()
    {
    }

    /**
     * Starting a new creation. AjaxResult Objects
     * 
     * @param code state code
     * @param msg Return to Content
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * Starting a new creation. AjaxResult Objects
     * 
     * @param code state code
     * @param msg Return to Content
     * @param data Data Objects
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * Return to Success News
     * 
     * @return Success news
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("Operations Successful");
    }

    /**
     * Return of successful data.
     * 
     * @return Success news
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("Operations Successful", data);
    }

    /**
     * Return to Success News
     * 
     * @param msg Return to Content
     * @return Success news
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * Return to Success News
     * 
     * @param msg Return to Content
     * @param data Data Objects
     * @return Success news
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * Return to warning.
     *
     * @param msg Return to Content
     * @return warning news.
     */
    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    /**
     * Return to warning.
     *
     * @param msg Return to Content
     * @param data Data Objects
     * @return warning news.
     */
    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }

    /**
     * Returning the wrong message.
     * 
     * @return The wrong news.
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("Operations fail.");
    }

    /**
     * Returning the wrong message.
     * 
     * @param msg Return to Content
     * @return The wrong news.
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * Returning the wrong message.
     * 
     * @param msg Return to Content
     * @param data Data Objects
     * @return The wrong news.
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * Returning the wrong message.
     * 
     * @param code state code
     * @param msg Return to Content
     * @return The wrong news.
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }

    /**
     * Good news for success.
     *
     * @return Results
     */
    public boolean isSuccess()
    {
        return Objects.equals(HttpStatus.SUCCESS, this.get(CODE_TAG));
    }

    /**
     * A warning message.
     *
     * @return Results
     */
    public boolean isWarn()
    {
        return Objects.equals(HttpStatus.WARN, this.get(CODE_TAG));
    }

    /**
     * Is it a wrong message?
     *
     * @return Results
     */
    public boolean isError()
    {
        return Objects.equals(HttpStatus.ERROR, this.get(CODE_TAG));
    }

    /**
     * Easy chain calling.
     *
     * @param key Key to
     * @param value Value
     * @return Data Objects
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
