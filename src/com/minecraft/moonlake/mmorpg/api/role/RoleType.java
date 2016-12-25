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
 
  
package com.minecraft.moonlake.mmorpg.api.role;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.role.excel.Assassin;
import com.minecraft.moonlake.mmorpg.api.role.excel.Magician;
import com.minecraft.moonlake.mmorpg.api.role.excel.Warrior;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/14.
 */
public enum RoleType {

    /**
     * MMORPG 角色: 刺客
     */
    ASSASSIN("Assassin", "刺客", "AS", Assassin.class),
    /**
     * MMORPG 角色: 魔法师
     */
    MAGICIAN("Magician", "魔法师", "MG", Magician.class),
    /**
     * MMORPG 角色: 战士
     */
    WARRIOR("Warrior", "战士", "WR", Warrior.class),
    ;

    private String type;
    private String name;
    private String prefix;
    private Class<? extends Role> clazz;
    private final static Map<String, RoleType> TYPE_MAP;
    private final static Map<String, RoleType> NAME_MAP;

    static {

        TYPE_MAP = new HashMap<>();
        NAME_MAP = new HashMap<>();

        for(RoleType roleType : values()) {

            TYPE_MAP.put(roleType.type.toLowerCase(), roleType);
            NAME_MAP.put(roleType.name.toLowerCase(), roleType);
        }
    }

    RoleType(String type, String name, String prefix, Class<? extends Role> clazz) {

        this.type = type;
        this.name = name;
        this.prefix = prefix;
        this.clazz = clazz;
    }

    /**
     * 获取 MMORPG 角色类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取 MMORPG 角色中文名
     *
     * @return 中文名
     */
    public String getName() {

        return name;
    }

    /**
     * 获取 MMORPG 角色前缀名
     *
     * @return 前缀名
     */
    public String getPrefix() {

        return prefix;
    }

    /**
     * 获取 MMORPG 角色子类对象类
     *
     * @return 子类
     */
    public Class<? extends Role> getClazz() {

        return clazz;
    }

    /**
     * 创建 MMORPG 角色子类对象
     *
     * @return 子类对象
     */
    public <T extends Role> T newInstance() {

        T role = null;

        try {

            role = (T)getClazz().newInstance();
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("实例化抽象角色时异常: " + e.getMessage());

            if(MMORPGPlugin.getInstances().isDebug()) {

                e.printStackTrace();
            }
        }
        return role;
    }

    /**
     * 将字符串序列化为角色类型
     *
     * @param type 类型
     * @return RoleType
     */
    public static RoleType fromType(String type) {

        return TYPE_MAP.get(type.toLowerCase());
    }

    /**
     * 将字符串序列化为角色类型
     *
     * @param name 名称
     * @return RoleType
     */
    public static RoleType fromName(String name) {

        return NAME_MAP.get(name.toLowerCase());
    }
}
