package com.ruoyi.common.exception;

/**
 * All unusual.
 * 
 * @author ruoyi
 */
public class GlobalException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

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
    public GlobalException()
    {
    }

    public GlobalException(String message)
    {
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public GlobalException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public GlobalException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}