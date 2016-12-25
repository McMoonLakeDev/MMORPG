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
import com.minecraft.moonlake.mmorpg.api.event.player.expendable.PlayerExpendableTpBookEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.TpBook;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.TpBookManager;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by MoonLake on 2016/6/15.
 */
public class PlayerTpBookListener implements Listener {

    private final MMORPG main;
    private final int checkNearbyMob;

    public PlayerTpBookListener(MMORPG main) {

        this.main = main;
        this.checkNearbyMob = ConfigManager.get("TpBook.CheckNearbyMob").asInt();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
            if(mmorpgPlayer == null) return;
            if(mmorpgPlayer.hasParticleTask()) return;

            ItemStack item = event.getItem();
            if(item == null || item.getType() == Material.AIR) return;

            TpBook tpBook = TpBookManager.getTpBook(item);
            if(tpBook != null) {

                event.setCancelled(true);
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);

                if(checkNearbyMob > 0) {

                    List<Entity> entityList = mmorpgPlayer.getNearbyEntities(checkNearbyMob, checkNearbyMob, checkNearbyMob);
                    boolean result = false;

                    if(entityList != null && !entityList.isEmpty()) {

                        for(Entity entity : entityList) {

                            result = entity != null && entity instanceof Monster;

                            if(result) {

                                break;
                            }
                        }
                    }
                    if(result) {
                        // the nearby have monster
                        mmorpgPlayer.l18n("player.expendable.toBook.haveMob");
                        return;
                    }
                }
                // call player expendable tpBook event
                PlayerExpendableTpBookEvent petbe = new PlayerExpendableTpBookEvent(mmorpgPlayer, tpBook);
                Bukkit.getServer().getPluginManager().callEvent(petbe);

                if(!petbe.isCancelled()) {

                    tpBook.useItem(mmorpgPlayer);
                }
            }
        }
    }
}
