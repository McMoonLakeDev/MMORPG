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
import org.bukkit.entity.Rabbit;

/**
 * Created by MoonLake on 2016/7/3.
 */
public class MobRabbitKiller extends MobRabbit {

    public MobRabbitKiller(org.bukkit.entity.Entity entity) {

        super(MobType.RABBIT_KILLER, entity);

        asLiving().setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
    }

    /**
     * 获取此怪物兔子是否为杀手兔
     *
     * @return true 则是杀手兔 else 不是
     */
    public boolean isKiller() {

        return true;
    }

    @Override
    public RabbitType getRabbitType() {

        return RabbitType.KILLER;
    }

    @Override
    public void setRabbitType(RabbitType rabbitType) {


    }
}
