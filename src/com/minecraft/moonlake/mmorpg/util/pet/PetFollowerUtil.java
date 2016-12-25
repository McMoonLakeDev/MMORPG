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
 
  
package com.minecraft.moonlake.mmorpg.util.pet;

import com.minecraft.moonlake.mmorpg.api.entity.ai.PetFollower;
import com.minecraft.moonlake.mmorpg.api.pet.Pet;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.task.MMORPGTask;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.PathEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;

/**
 * Created by MoonLake on 2016/5/21.
 */
public final class PetFollowerUtil extends MMORPGTask implements PetFollower {

    private final Pet pet;
    private final MMORPGPlayer player;

    public PetFollowerUtil(Pet pet, MMORPGPlayer player) {

        this.pet = pet;
        this.player = player;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        follow(player);
    }

    /**
     * 将此宠物跟随指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    @Override
    public void follow(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer == null || pet == null || pet.getEntity() == null) return;

        Location target = mmorpgPlayer.getLocation();
        ((EntityInsentient)((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle()).getNavigation().a(2d);
        PathEntity path = ((EntityInsentient)((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle()).getNavigation().a(target.getX() + 1d, target.getY(), target.getZ() + 1d);

        try {

            int distance = (int)mmorpgPlayer.getLocation().distance(pet.getEntity().getLocation());

            if((distance >= 30 && ((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle().valid) || pet.getEntity().getLocation().getBlock().isLiquid()) {

                ((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle().setLocation(target.getX(), target.getY(), target.getZ(), 0f, 0f);
            }

            if(path != null && distance >= 15 && distance < 30) {

                ((EntityInsentient)((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle()).getNavigation().a(path, 3d);
                ((EntityInsentient)((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle()).getNavigation().a(3d);
            }

            if(path != null && distance > 3 && distance < 15) {

                ((EntityInsentient)((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle()).getNavigation().a(path, 2d);
                ((EntityInsentient)((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle()).getNavigation().a(2d);
            }
        }
        catch (Exception e) {

            ((CraftEntity)pet.getEntity().getBukkitEntity()).getHandle().setLocation(target.getBlockX(), target.getBlockY(), target.getBlockZ(), 0f, 0f);

            if(getInstance().isDebug()) {

                e.printStackTrace();
            }
        }
    }

    /**
     * 获取线程任务
     *
     * @return Task
     */
    @Override
    public Runnable getTask() {

        return this;
    }
}
