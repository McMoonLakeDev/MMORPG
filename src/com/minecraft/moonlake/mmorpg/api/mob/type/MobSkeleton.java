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
import org.bukkit.entity.Skeleton;

/**
 * Created by MoonLake on 2016/6/29.
 */
public class MobSkeleton extends AbstractMob {

    public MobSkeleton(org.bukkit.entity.Entity entity) {

        this(entity, Skeleton.SkeletonType.NORMAL);
    }

    public MobSkeleton(org.bukkit.entity.Entity entity, Skeleton.SkeletonType skeletonType) {

        this(MobType.SKELETON, entity, skeletonType);
    }

    protected MobSkeleton(MobType mobType, org.bukkit.entity.Entity entity, Skeleton.SkeletonType skeletonType) {

        super(mobType, entity);

        asLiving().setSkeletonType(skeletonType);
    }

    @Override
    public Skeleton asLiving() {

        return (Skeleton) super.asLiving();
    }

    /**
     * 获取此怪物骷髅是否为凋零骷髅
     *
     * @return true 则为凋零骷髅 else 不是
     */
    public boolean isWither() {

        return false;
    }

    /**
     * 获取此怪物骷髅是否为流髑骷髅
     *
     * @return true 则为流髑骷髅 else 不是
     */
    public boolean isStray() {

        return false;
    }
}
