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

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import org.bukkit.event.HandlerList;

/**
 * Created by MoonLake on 2016/6/9.
 */
public class PlayerSkillUpgradeEvent extends MMORPGPlayerSkillEvent {

    private final static HandlerList handlers = new HandlerList();
    private final int skillLevel;

    public PlayerSkillUpgradeEvent(MMORPGPlayer mmorpgPlayer, Skill skill) {

        super(mmorpgPlayer, skill);

        this.skillLevel = skill.getLevel();
    }

    /**
     * 获取玩家技能升级后的新等级
     *
     * @return 技能等级
     */
    public int getSkillLevel() {

        return skillLevel;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;
    }

    public static HandlerList getHandlerList() {

        return handlers;
    }
}
