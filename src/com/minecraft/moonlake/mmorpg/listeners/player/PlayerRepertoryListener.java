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
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by MoonLake on 2016/6/18.
 */
public class PlayerRepertoryListener implements Listener {

    private final MMORPG main;

    public PlayerRepertoryListener(MMORPG main) {

        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {

        if(event.isCancelled()) return;
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block block = event.getClickedBlock();
            if(block == null || block.getType() != Material.ENDER_CHEST) return;

            MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
            if(mmorpgPlayer == null) return;
            if(mmorpgPlayer.hasParticleTask()) return;

            event.setCancelled(true);

            mmorpgPlayer.getRepertory().open();
            mmorpgPlayer.playSound(Sound.BLOCK_CHEST_OPEN, 10f, 1f);
        }
    }
}
