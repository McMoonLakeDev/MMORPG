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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.active;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.AbstractSkill;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTarget;
import org.bukkit.entity.LivingEntity;

/**
 * Created by MoonLake on 2016/6/1.
 */
public class Cleave extends AbstractSkill implements SkillTarget {

    public Cleave() {

        super("Cleave");

        setMagic(5);
        setDisplayName("顺劈斩");
        setDescription("对面前范围的目标造成 (100 + 等级)% 的伤害并击退一定距离.");
        setCombo(new SkillComboType[] { SkillComboType.SHIFT, SkillComboType.LEFT });
    }

    /**
     * 释放此技能
     *
     * @param owner 释放者
     */
    @Override
    public void cast(MMORPGPlayer owner) {


    }

    /**
     * 释放此技能
     *
     * @param owner  释放者
     * @param target 目标
     */
    @Override
    public void cast(MMORPGPlayer owner, LivingEntity target) {


    }

    /**
     * 获取此技能的范围
     *
     * @return 范围
     */
    @Override
    public double getRange() {

        return 3d;
    }
}
