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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.target;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.entity.LivingEntity;

import java.util.List;

/**
 * Created by MoonLake on 2016/6/1.
 */
public interface SkillTargets extends SkillRange {

    /**
     * 释放此技能
     *
     * @param owner 释放者
     * @param target 目标集合
     */
    void cast(MMORPGPlayer owner, List<LivingEntity> target);

    /**
     * 获取此技能的范围
     *
     * @return 范围
     */
    double getRange();
}
