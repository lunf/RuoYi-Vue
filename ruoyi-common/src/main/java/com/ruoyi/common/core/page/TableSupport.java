package com.ruoyi.common.core.page;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ServletUtils;

/**
 * Form Data Processing
 * 
 * @author ruoyi
 */
public class TableSupport
{
    /**
     * Current Record Start Index
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * Each page shows record numbers.
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * in order.
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * The direction of order. "desc" or "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Rationalization of page parameters
     */
    public static final String REASONABLE = "reasonable";

    /**
     * Covered page objects.
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1));
        pageDomain.setPageSize(Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
