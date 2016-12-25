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
 
  
package com.minecraft.moonlake.mmorpg.api.event.player.skill;

import com.minecraft.moonlake.mmorpg.api.event.player.MMORPGPlayerEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;

/**
 * Created by MoonLake on 2016/6/9.
 */
public abstract class MMORPGPlayerSkillEvent extends MMORPGPlayerEvent {

    private final Skill skill;
    private final String skillName;

    public MMORPGPlayerSkillEvent(MMORPGPlayer mmorpgPlayer, Skill skill) {

        super(mmorpgPlayer);

        this.skill = skill;
        this.skillName = skill.getName();
    }

    /**
     * 获取技能事件的玩家技能对象
     *
     * @return 技能对象
     */
    public final Skill getSkill() {

        return skill;
    }

    /**
     * 获取技能事件的玩家技能名
     *
     * @return 技能名
     */
    public final String getSkillName() {

        return skillName;
    }
}
