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
 
  
package com.minecraft.moonlake.mmorpg.api.mount.type;

import com.minecraft.moonlake.mmorpg.api.mount.AbstractMount;
import com.minecraft.moonlake.mmorpg.api.mount.MountType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/19.
 */
public class MountSkeletonHorse extends AbstractMount {

    public MountSkeletonHorse() {

        super(MountType.SKELETON_HORSE);
    }

    public MountSkeletonHorse(String displayName) {

        this(displayName, null);
    }

    public MountSkeletonHorse(MMORPGPlayer owner) {

        super(MountType.SKELETON_HORSE, owner);
    }

    public MountSkeletonHorse(String displayName, MMORPGPlayer owner) {

        super(MountType.SKELETON_HORSE, displayName, owner);
    }

    /**
     * 将坐骑召唤到拥有者位置
     */
    @Override
    public void onSummon() {

        super.onSummon();

        Horse horse = (Horse) getMount().getBukkitEntity();
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setVariant(Horse.Variant.SKELETON_HORSE);
        horse.setStyle(Horse.Style.NONE);
    }
}
