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
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.passive.Passive;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.Ultimate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/5/14.
 */
public abstract class AbstractRole implements Role {

    private final RoleType type;

    /**
     * 月色之湖 MMORPG 抽象角色
     *
     * @param type 角色类型
     */
    public AbstractRole(RoleType type) {

        this.type = type;
    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    public MMORPG getInstance() {

        return MMORPGPlugin.getInstances();
    }

    /**
     * 获取该职业的职业类型
     *
     * @return 职业类型
     */
    public RoleType getType() {

        return type;
    }

    /**
     * 是否是该职业的唯一武器类型
     *
     * @param item 物品栈
     * @return true 则物品栈是职业对应的武器类型 else 不是职业对应的武器类型
     */
    public boolean isOnlyWeapon(ItemStack item) {

        return item != null && isOnlyWeapon(item.getType());
    }

    /**
     * 是否是该职业的唯一武器类型
     *
     * @param type 物品栈类型
     * @return true 则物品栈是职业对应的武器类型 else 不是职业对应的武器类型
     */
    @Override
    public boolean isOnlyWeapon(Material type) {

        return type != null && type == getOnlyWeapon();
    }

    /**
     * 获取该职业的唯一武器类型
     *
     * @return 武器类型
     */
    public abstract Material getOnlyWeapon();

    /**
     * 获取该职业专属的技能数组
     *
     * @return 职业技能数组
     */
    public abstract Skill[] getRoleSkills();

    /**
     * 获取该职业的专属天赋数组
     *
     * @return 职业天赋数组
     */
    public abstract Talent[] getRoleTalents();

    /**
     * 获取该职业的专属被动技能
     *
     * @return 职业被动技能
     */
    public abstract Passive getRolePassive();

    /**
     * 获取该职业的专属技能大招
     *
     * @return 职业技能大招
     */
    public abstract Ultimate getRoleUltimate();
}
