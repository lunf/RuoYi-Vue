package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysMenuService;

/**
 * Menu Information
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    /**
     * Get a menu list.
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
        return success(menus);
    }

    /**
     * Detailed information according to the menu number.
     */
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId)
    {
        return success(menuService.selectMenuById(menuId));
    }

    /**
     * Get the menu to drop the tree list
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
        return success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * Load the corresponding role menu list trees
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<SysMenu> menus = menuService.selectMenuList(getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * Added Menu
     */
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "Management of Menu", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMenu menu)
    {
        if (!menuService.checkMenuNameUnique(menu))
        {
            return error("Added Menu'" + menu.getMenuName() + "'Failure，The menu name already exists.");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            return error("Added Menu'" + menu.getMenuName() + "'Failure，The address must behttp(s)://Beginning");
        }
        menu.setCreateBy(getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * Modifying the menu
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "Management of Menu", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMenu menu)
    {
        if (!menuService.checkMenuNameUnique(menu))
        {
            return error("Modifying the menu'" + menu.getMenuName() + "'Failure，The menu name already exists.");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            return error("Modifying the menu'" + menu.getMenuName() + "'Failure，The address must behttp(s)://Beginning");
        }
        else if (menu.getMenuId().equals(menu.getParentId()))
        {
            return error("Modifying the menu'" + menu.getMenuName() + "'Failure，The top menu cannot choose itself.");
        }
        menu.setUpdateBy(getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * Remove the menu.
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "Management of Menu", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") Long menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return warn("The existing menu.,Not allowed to remove.");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return warn("The menu is allocated.,Not allowed to remove.");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }
}