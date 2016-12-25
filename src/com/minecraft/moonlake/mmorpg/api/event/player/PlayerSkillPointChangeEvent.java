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
 
  
package com.minecraft.moonlake.mmorpg.api.event.player;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.event.HandlerList;

/**
 * Created by MoonLake on 2016/6/1.
 */
public class PlayerSkillPointChangeEvent extends MMORPGPlayerEvent {

    private final static HandlerList handlers = new HandlerList();
    private final int skillPoint;

    public PlayerSkillPointChangeEvent(MMORPGPlayer mmorpgPlayer, int skillPoint) {

        super(mmorpgPlayer);

        this.skillPoint = skillPoint;
    }

    /**
     * 获取此玩家的当前的技能点
     *
     * @return 技能点
     */
    public int getSkillPoint() {

        return skillPoint;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;
    }

    public static HandlerList getHandlerList() {

        return handlers;
    }
}
