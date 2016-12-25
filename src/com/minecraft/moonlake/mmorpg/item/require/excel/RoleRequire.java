/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
  
package com.minecraft.moonlake.mmorpg.item.require.excel;

import com.minecraft.moonlake.mmorpg.item.require.AbstractRequire;
import com.minecraft.moonlake.mmorpg.item.require.RequireType;
import com.minecraft.moonlake.mmorpg.api.role.RoleType;

/**
 * 物品特殊需求属性: 角色需求
 * Created by MoonLake on 2016/5/14.
 */
public class RoleRequire extends AbstractRequire {

    private RoleType roleType;

    /**
     * 物品特殊需求属性: 角色需求
     */
    public RoleRequire() {

        this(RoleType.WARRIOR);
    }

    /**
     * 物品特殊需求属性: 角色需求
     *
     * @param type 角色类型
     */
    public RoleRequire(RoleType type) {

        this.roleType = type;
    }

    /**
     * 设置角色需求类型
     *
     * @param roleType 角色类型
     */
    public void setRoleType(RoleType roleType) {

        this.roleType = roleType;
    }

    /**
     * 获取角色需求类型
     *
     * @return 角色类型
     */
    public RoleType getRoleType() {

        return roleType;
    }

    /**
     * 获取物品特殊需求属性类型
     *
     * @return 属性类型
     */
    @Override
    public RequireType getType() {

        return RequireType.ROLE_REQUIRE;
    }

    /**
     * 获取物品特殊需求属性类型值
     *
     * @return 属性类型值
     */
    @Override
    public String getRequireValue() {

        return getRoleType().getName();
    }
}
