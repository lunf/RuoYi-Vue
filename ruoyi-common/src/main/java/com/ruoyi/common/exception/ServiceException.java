package com.ruoyi.common.exception;

/**
 * Unusual business.
 * 
 * @author ruoyi
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * The error code.
     */
    private Integer code;

    /**
     * The wrong advice.
     */
    private String message;

    /**
     * The mistake is clear.，Internal Drop Error
     *
     * and {@link CommonResult#getDetailMessage()} The consistent design.
     */
    private String detailMessage;

    /**
     * Method of construction.，Avoid Anti-Series Problems
     */
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}