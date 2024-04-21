package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Routing information.
 * 
 * @author ruoyi
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo
{
    /**
     * The router name.
     */
    private String name;

    /**
     * The routing address.
     */
    private String path;

    /**
     * Hidden routes.，When set up true The router will no longer appear on the side bar.
     */
    private boolean hidden;

    /**
     * Return to address.，When set up noRedirect When the route is not clicked in the bread shell navigation.
     */
    private String redirect;

    /**
     * The component address
     */
    private String component;

    /**
     * The routing parameters.：as {"id": 1, "name": "ry"}
     */
    private String query;

    /**
     * When you have a route below. children The statement is greater than1At one time，It automatically becomes an embedded pattern.--Like the component page.
     */
    private Boolean alwaysShow;

    /**
     * Other Elements
     */
    private MetaVo meta;

    /**
     * The child route.
     */
    private List<RouterVo> children;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public boolean getHidden()
    {
        return hidden;
    }

    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }

    public String getRedirect()
    {
        return redirect;
    }

    public void setRedirect(String redirect)
    {
        this.redirect = redirect;
    }

    public String getComponent()
    {
        return component;
    }

    public void setComponent(String component)
    {
        this.component = component;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public Boolean getAlwaysShow()
    {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow)
    {
        this.alwaysShow = alwaysShow;
    }

    public MetaVo getMeta()
    {
        return meta;
    }

    public void setMeta(MetaVo meta)
    {
        this.meta = meta;
    }

    public List<RouterVo> getChildren()
    {
        return children;
    }

    public void setChildren(List<RouterVo> children)
    {
        this.children = children;
    }
}
