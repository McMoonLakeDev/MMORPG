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
 
  
package com.minecraft.moonlake.mmorpg.api.event.player.region;

import org.bukkit.event.HandlerList;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.region.mob.MobRegion;

/**
 * Created by MoonLake on 2016/07/04.
 */
public class PlayerEnterMobRegionEvent extends PlayerEnterRegionEvent {
	
	private final static HandlerList handlers = new HandlerList();

    public PlayerEnterMobRegionEvent(MMORPGPlayer mmorpgPlayer, MobRegion mobRegion) {
    	
    	super(mmorpgPlayer, mobRegion);
    }
    
    /**
     * 获取怪物区域对象
     *
     * @return 怪物区域
     */
    public MobRegion getRegion() {

        return (MobRegion) super.getRegion();
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;
    }

    public static HandlerList getHandlerList() {

        return handlers;
    }
}
