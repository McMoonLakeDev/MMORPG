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
 
  
package com.minecraft.moonlake.mmorpg.nms;

import com.minecraft.moonlake.mmorpg.util.StringUtil;
import net.minecraft.server.v1_10_R1.EntityItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/28.
 */
public class DropItem extends EntityItem {

    public DropItem(World world) {

        super(((CraftWorld)world).getHandle());
    }

    public DropItem(World world, double x, double y, double z) {

        this(world, new Location(world, x, y, z), new ItemStack(Material.AIR));
    }

    public DropItem(World world, double x, double y, double z, ItemStack item) {

        this(world, new Location(world, x, y, z), item);
    }

    public DropItem(World world, Location location, ItemStack item) {

        super(((CraftWorld)world).getHandle(), location.getX(), location.getY(), location.getZ(), CraftItemStack.asNMSCopy(item));
    }

    public void setPickupDelay(int delay) {

        pickupDelay = delay;
    }

    @Override
    public void setCustomName(String name) {

        super.setCustomName(StringUtil.color(name));
    }

    @Override
    public void setCustomNameVisible(boolean flag) {

        super.setCustomNameVisible(flag);
    }

    public Item drop() {

        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        return ((Item)getBukkitEntity());
    }
}
