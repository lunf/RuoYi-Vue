package com.ruoyi.system.service;

import java.util.List;
import java.util.Set;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.system.domain.vo.RouterVo;

/**
 * The menu The business level.
 * 
 * @author ruoyi
 */
public interface ISysMenuService
{
    /**
     * User request system menu list
     * 
     * @param userId UsersID
     * @return The menu list
     */
    public List<SysMenu> selectMenuList(Long userId);

    /**
     * User request system menu list
     * 
     * @param menu Menu Information
     * @param userId UsersID
     * @return The menu list
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * According to UsersIDRequest authority
     * 
     * @param userId UsersID
     * @return List of permissions
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * According to the roleIDRequest authority
     * 
     * @param roleId The roleID
     * @return List of permissions
     */
    public Set<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * According to UsersIDAsk for menu tree information
     * 
     * @param userId UsersID
     * @return The menu list
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * According to the roleIDAsk for menu tree information
     * 
     * @param roleId The roleID
     * @return Select the menu list.
     */
    public List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * Building the menu required by the front router
     * 
     * @param menus The menu list
     * @return The routing list.
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * The building of the front structure is required.
     * 
     * @param menus The menu list
     * @return List of tree structures
     */
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * The building of the front end requires the drawing tree structure.
     * 
     * @param menus The menu list
     * @return List of tree structures
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * According to the menu.IDAsk for information
     * 
     * @param menuId The menuID
     * @return Menu Information
     */
    public SysMenu selectMenuById(Long menuId);

    /**
     * Is there a menu nodes?
     * 
     * @param menuId The menuID
     * @return Results true existing false There is no
     */
    public boolean hasChildByMenuId(Long menuId);

    /**
     * Ask if the menu has a role.
     * 
     * @param menuId The menuID
     * @return Results true existing false There is no
     */
    public boolean checkMenuExistRole(Long menuId);

    /**
     * Add to Save Menu Information
     * 
     * @param menu Menu Information
     * @return Results
     */
    public int insertMenu(SysMenu menu);

    /**
     * Modify the menu information
     * 
     * @param menu Menu Information
     * @return Results
     */
    public int updateMenu(SysMenu menu);

    /**
     * Remove Menu Management Information
     * 
     * @param menuId The menuID
     * @return Results
     */
    public int deleteMenuById(Long menuId);

    /**
     * Is the test menu name unique?
     * 
     * @param menu Menu Information
     * @return Results
     */
    public boolean checkMenuNameUnique(SysMenu menu);
}
