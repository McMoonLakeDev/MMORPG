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
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.RenameCard;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.RenameCardManager;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/20.
 */
public class PlayerMountListener implements Listener {

    private final MMORPG main;

    public PlayerMountListener(MMORPG main) {

        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
            if(mmorpgPlayer == null) return;
            if(mmorpgPlayer.hasParticleTask()) return;

            ItemStack item = mmorpgPlayer.getItemInMainHand();
            if(ItemManager.isAir(item)) return;
            RenameCard renameCard = RenameCardManager.getRenameCard(item);
            if(renameCard == null) return;

            Mount mount = mmorpgPlayer.getMount().getMain();

            if(mount == null) {

                mmorpgPlayer.l18n("player.mount.rename.notMain");
                return;
            }
            if(!mount.isLiving()) {

                mmorpgPlayer.l18n("player.mount.rename.notRide");
                return;
            }
            event.setCancelled(true);
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);

            renameCard.useItem(mmorpgPlayer);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if(EntityManager.isMount(event.getEntity())) {

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExit(VehicleExitEvent event) {

        if(EntityManager.isMount(event.getVehicle())) {

            event.getVehicle().remove();
        }
    }

    @EventHandler
    public void onHorse(InventoryClickEvent event) {

        if(event.getInventory() == null) return;
        if(event.getClickedInventory() == null) return;
        if(!(event.getClickedInventory() instanceof HorseInventory)) return;
        if(!(event.getWhoClicked() instanceof Player)) return;

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getWhoClicked().getName());

        if(mmorpgPlayer == null) return;
        if(mmorpgPlayer.getMount().getMain() == null) return;
        if(!mmorpgPlayer.getMount().getMain().isLiving()) return;

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
    }
}
