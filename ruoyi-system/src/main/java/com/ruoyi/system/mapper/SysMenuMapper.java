package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysMenu;

/**
 * The menu table The Data Layer
 *
 * @author ruoyi
 */
public interface SysMenuMapper
{
    /**
     * Search System Menu List
     *
     * @param menu Menu Information
     * @return The menu list
     */
    public List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * All permits of the user.
     *
     * @return List of permissions
     */
    public List<String> selectMenuPerms();

    /**
     * User request system menu list
     *
     * @param menu Menu Information
     * @return The menu list
     */
    public List<SysMenu> selectMenuListByUserId(SysMenu menu);

    /**
     * According to the roleIDRequest authority
     * 
     * @param roleId The roleID
     * @return List of permissions
     */
    public List<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * According to UsersIDRequest authority
     *
     * @param userId UsersID
     * @return List of permissions
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * According to UsersIDInquiry menu
     *
     * @return The menu list
     */
    public List<SysMenu> selectMenuTreeAll();

    /**
     * According to UsersIDInquiry menu
     *
     * @param userId UsersID
     * @return The menu list
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * According to the roleIDAsk for menu tree information
     * 
     * @param roleId The roleID
     * @param menuCheckStrictly Menu tree options are linked to display
     * @return Select the menu list.
     */
    public List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);

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
     * @return Results
     */
    public int hasChildByMenuId(Long menuId);

    /**
     * Add new menu information
     *
     * @param menu Menu Information
     * @return Results
     */
    public int insertMenu(SysMenu menu);

    /**
     * Modifying the menu information
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
     * @param menuName Name of Menu
     * @param parentId The Dad's MenuID
     * @return Results
     */
    public SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);
}
