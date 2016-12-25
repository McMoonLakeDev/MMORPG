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
import org.bukkit.entity.Cow;

/**
 * Created by MoonLake on 2016/7/3.
 */
public class MobCow extends AbstractMob {

    public MobCow(org.bukkit.entity.Entity entity) {

        this(MobType.COW, entity);
    }

    protected MobCow(MobType mobType, org.bukkit.entity.Entity entity) {

        super(mobType, entity);
    }

    @Override
    public Cow asLiving() {

        return (Cow) super.asLiving();
    }
}
