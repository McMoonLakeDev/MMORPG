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
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.type.SkillReqAttLevel;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTarget;
import org.bukkit.entity.LivingEntity;

/**
 * Created by MoonLake on 2016/6/6.
 */
public class Tearing extends AbstractSkill implements SkillTarget {

    public Tearing() {

        super("Tearing");

        setMagic(20);
        setDisplayName("撕裂");
        setDescription("对一个单体目标造成成吨的伤害( 150 + X% )");
        setCombo(new SkillComboType[] { SkillComboType.RIGHT, SkillComboType.RIGHT, SkillComboType.LEFT });
        addReqAtt(new SkillReqAttLevel(10));
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

        return 10d;
    }
}
