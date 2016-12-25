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
 
  
package com.minecraft.moonlake.mmorpg.api.mob.type;

import com.minecraft.moonlake.mmorpg.api.mob.AbstractMob;
import com.minecraft.moonlake.mmorpg.api.mob.MobType;
import org.bukkit.entity.Zombie;

/**
 * Created by MoonLake on 2016/6/29.
 */
public class MobZombie extends AbstractMob {

    public MobZombie(org.bukkit.entity.Entity entity) {

        this(MobType.ZOMBIE, entity);
    }

    protected MobZombie(MobType mobType, org.bukkit.entity.Entity entity) {

        super(mobType, entity);

        asLiving().setBaby(false);
    }

    @Override
    public Zombie asLiving() {

        return (Zombie) super.asLiving();
    }

    /**
     * 获取此怪物僵尸是否为猪人僵尸
     *
     * @return true 则是猪人僵尸 else 不是
     */
    public boolean isPig() {

        return false;
    }

    /**
     * 获取此怪物僵尸是否为尸壳僵尸
     *
     * @return true 则是尸壳僵尸 else 不是
     */
    public boolean isHusk() {

        return false;
    }

    /**
     * 获取此怪物僵尸是否为村民僵尸
     *
     * @return true 则是村民僵尸 else 不是
     */
    public boolean isVillager() {

        return false;
    }

    /**
     * 获取此怪物僵尸是否为小的
     *
     * @return true 则是小的 else 不是
     */
    public boolean isBaby() {

        return false;
    }
}
