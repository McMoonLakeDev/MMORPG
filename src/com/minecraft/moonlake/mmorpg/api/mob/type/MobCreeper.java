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
import org.bukkit.entity.Creeper;

/**
 * Created by MoonLake on 2016/7/2.
 */
public class MobCreeper extends AbstractMob {

    public MobCreeper(org.bukkit.entity.Entity entity) {

        this(MobType.CREEPER, entity);
    }

    protected MobCreeper(MobType mobType, org.bukkit.entity.Entity entity) {

        super(mobType, entity);
    }

    @Override
    public Creeper asLiving() {

        return (Creeper) super.asLiving();
    }

    /**
     * 获取此怪物苦力怕是否为闪电苦力怕
     *
     * @return true 则是闪电苦力怕 else 不是
     */
    public boolean isBolt() {

        return false;
    }
}
