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
 
  
package com.minecraft.moonlake.mmorpg.item.require;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.item.require.excel.*;

/**
 * Created by MoonLake on 2016/5/14.
 */
public enum RequireType {

    /**
     * 物品特殊要求: 角色需求 (设置物品栈的角色类型需求,则目标者角色和物品需求不同无法使用)
     */
    ROLE_REQUIRE("RoleRequire", "&7❈ 职业需求", RoleRequire.class),
    /**
     * 物品特殊要求: 最低等级 (设置物品栈的最低等级使用需求,如果目标者等级不足够无法使用)
     */
    MIN_LEVEL("MinLevel", "&7❈ 使用等级", MinLevel.class),
    /**
     * 物品特殊要求: 不可交易 (设置物品栈的不可交易需求,则目标者无法将物品进行贩卖出售)
     */
    NOT_TRADE("NotTrade", "&7❈ 是否可交易", NotTrade.class),
    /**
     * 物品特殊要求: 不可丢弃 (设置物品栈的不可丢弃需求,则目标者无法将物品进行丢弃)
     */
    NOT_DISCARD("NotDiscard", "&7❈ 是否可丢弃", NotDiscard.class),
    ;

    private String type;
    private String name;
    private Class<? extends AbstractRequire> clazz;

    RequireType(String type, String name, Class<? extends AbstractRequire> clazz) {

        this.type = type;
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * 获取 MMORPG 物品需求类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取 MMORPG 物品需求中文名
     *
     * @return 中文名
     */
    public String getName() {

        return name;
    }

    /**
     * 获取 MMORPG 物品需求子类对象类
     *
     * @return 子类
     */
    public Class<? extends AbstractRequire> getClazz() {

        return clazz;
    }

    /**
     * 创建 MMORPG 物品需求子类对象
     *
     * @return 子类对象
     */
    public <T extends ItemRequire> T newInstance() {

        T require = null;

        try {

            require = (T)clazz.newInstance();
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("实例化抽象物品需求时异常: " + e.getMessage());
        }
        return require;
    }
}
