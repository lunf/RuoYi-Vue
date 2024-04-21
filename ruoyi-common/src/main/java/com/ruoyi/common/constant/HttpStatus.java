package com.ruoyi.common.constant;

/**
 * Return to state code.
 * 
 * @author ruoyi
 */
public class HttpStatus
{
    /**
     * Operations Successful
     */
    public static final int SUCCESS = 200;

    /**
     * Objects Create Success
     */
    public static final int CREATED = 201;

    /**
     * The request has been accepted.
     */
    public static final int ACCEPTED = 202;

    /**
     * Operations have been successful.，No returns of data.
     */
    public static final int NO_CONTENT = 204;

    /**
     * Resources have been removed.
     */
    public static final int MOVED_PERM = 301;

    /**
     * to redirect
     */
    public static final int SEE_OTHER = 303;

    /**
     * Resources are not modified.
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * Parameters list errors（Lack of，The format does not match.）
     */
    public static final int BAD_REQUEST = 400;

    /**
     * not authorized.
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Access is limited.，Permit expiration
     */
    public static final int FORBIDDEN = 403;

    /**
     * Resources，The service was not found.
     */
    public static final int NOT_FOUND = 404;

    /**
     * not allowed.httpMethod
     */
    public static final int BAD_METHOD = 405;

    /**
     * Resource conflict，Resources are locked.
     */
    public static final int CONFLICT = 409;

    /**
     * Unsupported data，Type of Media
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * Internal system errors
     */
    public static final int ERROR = 500;

    /**
     * Interface is not achieved.
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * System warning.
     */
    public static final int WARN = 601;
}
