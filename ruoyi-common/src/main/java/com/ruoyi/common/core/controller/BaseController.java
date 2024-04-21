package com.ruoyi.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sql.SqlUtil;

/**
 * webGeneral Data Processing
 * 
 * @author ruoyi
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * A string of characters in the date format transmitted to the front.，Automatically converted toDateType of
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date Type of Conversion
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * Set page request data
     */
    protected void startPage()
    {
        PageUtils.startPage();
    }

    /**
     * Set the request order data.
     */
    protected void startOrderBy()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy()))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * Clean the line variable of the page.
     */
    protected void clearPage()
    {
        PageUtils.clearPage();
    }

    /**
     * Reply to request page data
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("Successful search.");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * Return to Success
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * Return to Failed News
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * Return to Success News
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }
    
    /**
     * Return to Success News
     */
    public AjaxResult success(Object data)
    {
        return AjaxResult.success(data);
    }

    /**
     * Return to Failed News
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * Return to warning.
     */
    public AjaxResult warn(String message)
    {
        return AjaxResult.warn(message);
    }

    /**
     * Response to Results
     * 
     * @param rows The impact number.
     * @return Operation Results
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * Response to Results
     * 
     * @param result Results
     * @return Operation Results
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * Page is jumping.
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * Obtaining User Cache Information
     */
    public LoginUser getLoginUser()
    {
        return SecurityUtils.getLoginUser();
    }

    /**
     * Obtaining Login Usersid
     */
    public Long getUserId()
    {
        return getLoginUser().getUserId();
    }

    /**
     * Get the registration department.id
     */
    public Long getDeptId()
    {
        return getLoginUser().getDeptId();
    }

    /**
     * Get the login user name.
     */
    public String getUsername()
    {
        return getLoginUser().getUsername();
    }
}
