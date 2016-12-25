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

import com.minecraft.moonlake.mmorpg.api.mob.Mob;
import com.minecraft.moonlake.mmorpg.api.mob.MobManager;
import com.minecraft.moonlake.mmorpg.api.mob.MobType;
import com.minecraft.moonlake.mmorpg.api.mob.knight.MobKnight;

/**
 * Created by MoonLake on 2016/7/2.
 */
public class MobSpiderKnight extends MobSpider implements MobKnight {

    private Mob knight;

    public MobSpiderKnight(org.bukkit.entity.Entity entity) {

        this(MobType.SPIDER_KNIGHT, entity);
    }

    protected MobSpiderKnight(MobType mobType, org.bukkit.entity.Entity entity) {

        super(mobType, entity);
    }

    /**
     * 获取此怪物蜘蛛是否为蜘蛛骑士
     *
     * @return true 则为蜘蛛骑士 else 不是
     */
    public boolean isKnight() {

        return true;
    }

    /**
     * 设置此怪物蜘蛛骑士的骑士者
     *
     * @param spiderKnight 蜘蛛骑士类型
     */
    public <T extends MobSkeleton> T setKnight(Class<T> spiderKnight) {

        this.knight = MobManager.spawn(spiderKnight, getLocation());

        if(knight != null && !knight.isDead()) {

            setPassenger(knight);
        }
        return (T) getKnight();
    }

    /**
     * 获取此怪物蜘蛛骑士的骑士者
     *
     * @return 骑士者 没有则返回 null
     */
    public MobSkeleton getKnight() {

        return (MobSkeleton) knight;
    }

    /**
     * 将此怪物进行清除
     *
     * @return 是否死亡
     */
    @Override
    public boolean remove() {

        if(isKnight()) {

            getKnight().remove();
        }
        return super.remove();
    }
}
