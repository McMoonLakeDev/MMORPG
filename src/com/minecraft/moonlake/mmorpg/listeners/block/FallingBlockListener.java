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
 
  
package com.minecraft.moonlake.mmorpg.listeners.block;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/7/6.
 */
public class FallingBlockListener implements Listener {

    private final MMORPG main;
    private final List<FallingBlock> fallingBlockList;

    public FallingBlockListener(MMORPG main) {

        this.main = main;
        this.fallingBlockList = new ArrayList<>();
    }

    public List<FallingBlock> getFallingBlockList() {

        return fallingBlockList;
    }

    @EventHandler
    public void onFalling(EntityChangeBlockEvent event) {

        if(getFallingBlockList().contains(event.getEntity())) {

            event.setCancelled(true);
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.BLOCK_STONE_STEP, 5f, 1f);
            event.getEntity().remove();

            getFallingBlockList().remove(event.getEntity());
        }
    }
}
