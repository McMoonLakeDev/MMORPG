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
import org.bukkit.entity.Rabbit;

/**
 * Created by MoonLake on 2016/7/3.
 */
public class MobRabbit extends AbstractMob {

    private RabbitType rabbitType;

    public MobRabbit(org.bukkit.entity.Entity entity) {

        this(MobType.RABBIT, entity);
    }

    protected MobRabbit(MobType mobType, org.bukkit.entity.Entity entity) {

        super(mobType, entity);

        setRabbitType(RabbitType.WHITE);
    }

    @Override
    public Rabbit asLiving() {

        return (Rabbit) super.asLiving();
    }

    /**
     * 获取此怪物兔子是否为杀手兔
     *
     * @return true 则是杀手兔 else 不是
     */
    public boolean isKiller() {

        return false;
    }

    /**
     * 获取此怪物兔子的类型
     *
     * @return 类型
     */
    public RabbitType getRabbitType() {

        return rabbitType;
    }

    /**
     * 设置此怪物兔子的类型
     *
     * @param rabbitType 兔子类型
     */
    public void setRabbitType(RabbitType rabbitType) {

        if(rabbitType != RabbitType.KILLER) {

            this.rabbitType = rabbitType;
            this.asLiving().setRabbitType(Rabbit.Type.valueOf(rabbitType.name()));
        }
    }
}
