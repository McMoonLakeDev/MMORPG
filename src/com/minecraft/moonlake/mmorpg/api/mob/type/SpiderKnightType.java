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

/**
 * Created by MoonLake on 2016/7/3.
 */

import com.minecraft.moonlake.mmorpg.api.mob.Mob;

/**
 * 蜘蛛骑士的骑士者类型
 */
public enum SpiderKnightType {

    /**
     * 骑士类型: 骷髅
     */
    SKELETON(MobSkeleton.class),
    /**
     * 骑士类型: 流髑骷髅
     */
    SKELETON_STRAY(MobSkeletonStray.class),
    /**
     * 骑士类型: 凋零骷髅
     */
    SKELETON_WITHER(MobSkeletonWither.class),;

    private Class<? extends Mob> clazz;

    SpiderKnightType(Class<? extends Mob> clazz) {

        this.clazz = clazz;
    }

    public Class<? extends Mob> getClazz() {

        return clazz;
    }
}
