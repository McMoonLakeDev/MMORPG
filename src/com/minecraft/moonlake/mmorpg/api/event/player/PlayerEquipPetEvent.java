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

import com.minecraft.moonlake.mmorpg.api.pet.Pet;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Created by MoonLake on 2016/5/22.
 */
public class PlayerEquipPetEvent extends MMORPGPlayerEvent implements Cancellable {

    private final static HandlerList handlers = new HandlerList();
    private Pet pet;
    private boolean cancel = false;

    public PlayerEquipPetEvent(MMORPGPlayer player, Pet pet) {

        super(player);

        this.pet = pet;
    }

    /**
     * 获取玩家装备的宠物对象
     *
     * @return 宠物对象
     */
    public Pet getPet() {

        return pet;
    }

    @Override
    public boolean isCancelled() {

        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {

        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;
    }

    public static HandlerList getHandlerList() {

        return handlers;
    }
}
