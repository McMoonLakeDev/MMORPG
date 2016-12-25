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
 
  
package com.minecraft.moonlake.mmorpg.api.role.excel;

import com.minecraft.moonlake.mmorpg.api.role.AbstractRole;
import com.minecraft.moonlake.mmorpg.api.role.RoleType;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.passive.Passive;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.Ultimate;
import org.bukkit.Material;

/**
 * 月色之湖 MMORPG 角色类型: 魔法师
 * Created by MoonLake on 2016/5/14.
 */
public class Magician extends AbstractRole {

    private final Material weaponType;
    private final Skill[] skills;
    private final Talent[] talents;
    private final Passive passive;
    private final Ultimate ultimate;
    private final static RoleType type = RoleType.MAGICIAN;

    /**
     * MMOPRG角色: 魔法师
     */
    public Magician() {

        super(type);

        weaponType = Material.STICK;

        // skill, talent, passive, ultimate
        skills = new Skill[] { };
        talents = new Talent[] { };
        passive = null;
        ultimate = null;
    }

    /**
     * 获取该职业的唯一武器类型
     *
     * @return 武器类型
     */
    @Override
    public Material getOnlyWeapon() {

        return weaponType;
    }

    /**
     * 获取该职业专属的技能数组
     *
     * @return 职业技能数组
     */
    @Override
    public Skill[] getRoleSkills() {

        return skills;
    }

    /**
     * 获取该职业的专属天赋数组
     *
     * @return 职业天赋数组
     */
    @Override
    public Talent[] getRoleTalents() {

        return talents;
    }

    /**
     * 获取该职业的专属被动技能
     *
     * @return 职业被动技能
     */
    @Override
    public Passive getRolePassive() {

        return passive;
    }

    /**
     * 获取该职业的专属技能大招
     *
     * @return 职业技能大招
     */
    @Override
    public Ultimate getRoleUltimate() {

        return ultimate;
    }
}
