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
 
  
package com.minecraft.moonlake.mmorpg.listeners.player;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.event.player.region.PlayerEnterMobRegionEvent;
import com.minecraft.moonlake.mmorpg.api.event.player.region.PlayerEnterRegionEvent;
import com.minecraft.moonlake.mmorpg.api.event.player.region.PlayerLeaveMobRegionEvent;
import com.minecraft.moonlake.mmorpg.api.event.player.region.PlayerLeaveRegionEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.region.Region;
import com.minecraft.moonlake.mmorpg.api.region.RegionManager;
import com.minecraft.moonlake.mmorpg.api.region.RegionType;
import com.minecraft.moonlake.mmorpg.api.region.mob.MobRegion;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.VectorManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by MoonLake on 2016/5/27.
 */
public class PlayerRegionListener implements Listener {

    private final MMORPG main;
    private final double KNOCKBACK_POWER = 1.2d;

    public PlayerRegionListener(MMORPG main) {

        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {

        if(RegionManager.getRegionSize() <= 0) return;

        if(event.getFrom().getBlockX() == event.getTo().getBlockX() &&
            event.getFrom().getBlockY() == event.getTo().getBlockY() &&
            event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {

            return;
        }
        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
        if(mmorpgPlayer == null) return;

        Region region = RegionManager.hasRegion(event.getTo());
        if(region != null) {

            if(!RegionManager.getCacheCurrentMap().containsKey(event.getPlayer().getName())) {
            	
            	boolean flag = RegionManager.checkPlayerRegionFlag(mmorpgPlayer, region);
            	
                if(!flag) {
                	
                	leave(region, mmorpgPlayer, event.getFrom());
                	
                	return;
                }
                PlayerEnterRegionEvent pere = new PlayerEnterRegionEvent(mmorpgPlayer, region);
                Bukkit.getServer().getPluginManager().callEvent(pere);
                
                if(region.getType() == RegionType.MOB) {
                	
                	PlayerEnterMobRegionEvent pemre = new PlayerEnterMobRegionEvent(mmorpgPlayer, (MobRegion)region);
                	Bukkit.getServer().getPluginManager().callEvent(pemre);
                }
                if(!pere.isCancelled()) {

                    mmorpgPlayer.sendTitlePacket(StringUtil.color(region.getEnter()).replace("%region", region.getName()).replace("%player", mmorpgPlayer.getName()));
                    RegionManager.getCacheCurrentMap().put(event.getPlayer().getName(), region.getName());
                }
                else {

                	leave(region, mmorpgPlayer, event.getFrom());
                }
            }
        }
        else if(RegionManager.getCacheCurrentMap().containsKey(event.getPlayer().getName())) {

            Region cache = RegionManager.getRegionFromCache(RegionManager.getCacheCurrentMap().get(event.getPlayer().getName()));

            if(cache != null) {

                PlayerLeaveRegionEvent plre = new PlayerLeaveRegionEvent(mmorpgPlayer, cache);
                Bukkit.getServer().getPluginManager().callEvent(plre);

                if(cache.getType() == RegionType.MOB) {
                	
                	PlayerLeaveMobRegionEvent plmre = new PlayerLeaveMobRegionEvent(mmorpgPlayer, (MobRegion)region);
                	Bukkit.getServer().getPluginManager().callEvent(plmre);
                }
                if(!plre.isCancelled()) {

                    mmorpgPlayer.sendTitlePacket(StringUtil.color(cache.getLeave()).replace("%region", cache.getName()).replace("%player", mmorpgPlayer.getName()));
                    RegionManager.getCacheCurrentMap().remove(event.getPlayer().getName());
                }
                else {

                    enter(cache, mmorpgPlayer, event.getFrom());
                }
            }
        }
    }
    
    private void enter(Region region, MMORPGPlayer mmorpgPlayer, Location source) {
    	
    	if(region.getMainLocation() != null) {

    		mmorpgPlayer.teleport(region.getMainLocation());
        }
        else {

            VectorManager.knockBackVector(mmorpgPlayer, source, KNOCKBACK_POWER);
        }
    }
    
    private void leave(Region region, MMORPGPlayer mmorpgPlayer, Location source) {
    	
    	if(region.getBackLocation() != null) {

            mmorpgPlayer.teleport(region.getBackLocation());
        }
        else {

            VectorManager.knockBackVector(mmorpgPlayer, source, KNOCKBACK_POWER);
        }
    }
}
