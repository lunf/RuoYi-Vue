package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysRoleMenu;

/**
 * Character and Menu Relationship The Data Layer
 * 
 * @author ruoyi
 */
public interface SysRoleMenuMapper
{
    /**
     * Question menu number of use
     * 
     * @param menuId The menuID
     * @return Results
     */
    public int checkMenuExistRole(Long menuId);

    /**
     * through the role.IDRemove Character and Menu Relationships
     * 
     * @param roleId The roleID
     * @return Results
     */
    public int deleteRoleMenuByRoleId(Long roleId);

    /**
     * Collective Delete Character Menu Related Information
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteRoleMenu(Long[] ids);

    /**
     * Add new roles menu information
     * 
     * @param roleMenuList List of Character Menu
     * @return Results
     */
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
