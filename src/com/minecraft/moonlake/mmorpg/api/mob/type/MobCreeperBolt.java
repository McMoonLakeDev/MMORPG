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

import com.minecraft.moonlake.mmorpg.api.mob.MobType;
import org.bukkit.entity.Entity;

/**
 * Created by MoonLake on 2016/7/2.
 */
public class MobCreeperBolt extends MobCreeper {

    public MobCreeperBolt(Entity entity) {

        super(MobType.CREEPER_BOLT, entity);

        asLiving().setPowered(true);
    }

    /**
     * 获取此怪物苦力怕是否为闪电苦力怕
     *
     * @return true 则是闪电苦力怕 else 不是
     */
    @Override
    public boolean isBolt() {

        return true;
    }
}
