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
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.hotbar.HotBarItem;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * Created by MoonLake on 2016/5/20.
 */
public class PlayerHotbarListener implements Listener {

    private final MMORPG main;

    public PlayerHotbarListener(MMORPG main) {

        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(!event.hasItem() || event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            HotBarItem.HotBarType hotbarItem = HotBarItem.HotBarType.fromSlot(event.getPlayer().getInventory().getHeldItemSlot());
            if(hotbarItem != null) {

                MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
                if(mmorpgPlayer == null) return;
                if(mmorpgPlayer.hasParticleTask()) return;

                event.setCancelled(true);
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);

                if(mmorpgPlayer.isShift()) {

                    HotBarItem.runHotBarInteract(hotbarItem, HotBarItem.HotBarInteractType.SHIFT_RIGHT_CLICK, mmorpgPlayer);
                }
                else {

                    HotBarItem.runHotBarInteract(hotbarItem, HotBarItem.HotBarInteractType.RIGHT_CLICK, mmorpgPlayer);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {

        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getType() != InventoryType.PLAYER) return;
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType() == Material.AIR) return;

        HotBarItem.HotBarType hotbarItem = HotBarItem.HotBarType.fromSlot(event.getWhoClicked().getInventory().first(event.getCurrentItem()));
        if(hotbarItem != null) {

            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSwap(PlayerSwapHandItemsEvent event) {

        HotBarItem.HotBarType hotbarItem = HotBarItem.HotBarType.fromSlot(event.getPlayer().getInventory().getHeldItemSlot());
        if(hotbarItem != null) {

            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event) {

        HotBarItem.HotBarType hotbarItem = HotBarItem.HotBarType.fromSlot(event.getPlayer().getInventory().getHeldItemSlot());
        if(hotbarItem != null) {

            event.setCancelled(true);
        }
    }
}
