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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.ultimate.type;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTargets;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.AbstractUltimate;
import org.bukkit.entity.LivingEntity;

import java.util.List;

/**
 * Created by MoonLake on 2016/6/6.
 */
public class PolesFlip extends AbstractUltimate implements SkillTargets {

    public PolesFlip() {

        super("PolesFlip");

        setMagic(60);
        setDisplayName("两极翻转");
        setDescription("将附近所有怪物吸到一点眩晕怪物并从天而降闪电造成3倍攻击力伤害, 然后推开怪物，自身获得速度II 3秒");
        setCombo(new SkillComboType[] { SkillComboType.RIGHT, SkillComboType.RIGHT });
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
     * @param target 目标集合
     */
    @Override
    public void cast(MMORPGPlayer owner, List<LivingEntity> target) {

    }

    /**
     * 获取此技能的范围
     *
     * @return 范围
     */
    @Override
    public double getRange() {

        return 0;
    }
}
