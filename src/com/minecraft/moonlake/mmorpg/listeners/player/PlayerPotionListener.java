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
import com.minecraft.moonlake.mmorpg.api.event.player.expendable.PlayerExpendablePotionEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.Potion;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.PotionManager;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/15.
 */
public class PlayerPotionListener implements Listener {

    private final MMORPG main;

    public PlayerPotionListener(MMORPG main) {

        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
            if(mmorpgPlayer == null) return;
            if(mmorpgPlayer.hasParticleTask()) return;

            ItemStack item = event.getItem();
            if(item == null || item.getType() == Material.AIR) return;

            Potion potion = PotionManager.getPotion(item);
            if (potion != null) {

                event.setCancelled(true);
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);

                // call player expendable potion event
                PlayerExpendablePotionEvent pepe = new PlayerExpendablePotionEvent(mmorpgPlayer, potion);
                Bukkit.getServer().getPluginManager().callEvent(pepe);

                if(!pepe.isCancelled()) {

                    if (mmorpgPlayer.getLevel() < potion.getReqLevel()) {

                        mmorpgPlayer.l18n("player.expendable.reqLevel.notEnough", potion.getReqLevel());
                        return;
                    }
                    potion.useItem(mmorpgPlayer);
                    mmorpgPlayer.playSound(Sound.ENTITY_PLAYER_BURP, 10f, 1f);
                }
            }
        }
    }
}
