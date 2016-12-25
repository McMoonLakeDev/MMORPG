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
 
  
package com.minecraft.moonlake.mmorpg.api.mount;

import com.minecraft.moonlake.mmorpg.api.mount.type.*;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

/**
 * Created by MoonLake on 2016/6/19.
 */
public enum MountType {

    /**
     * 坐骑类型: 骡子
     */
    MULE("Mule", "坐骑 骡子", Horse.class, MountMule.class),
    /**
     * 坐骑类型: 普通马
     */
    HORSE("Horse", "坐骑 马", Horse.class, MountHorse.class),
    /**
     * 坐骑类型: 驴
     */
    DONKEY("Donkey", "坐骑 驴", Horse.class, MountDonkey.class),
    /**
     * 坐骑类型: 僵尸马
     */
    ZOMBIE_HORSE("ZombieHorse", "坐骑 僵尸马", Horse.class, MountZombieHorse.class),
    /**
     * 坐骑类型: 骷髅马
     */
    SKELETON_HORSE("SkeletonHorse", "坐骑 骷髅马", Horse.class, MountSkeletonHorse.class),
    ;

    private String type;
    private String name;
    private Class<? extends Entity> entity;
    private Class<? extends Mount> clazz;

    MountType(String type, String name, Class<? extends Entity> entity, Class<? extends Mount> clazz) {

        this.type = type;
        this.name = name;
        this.entity = entity;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public Class<? extends Entity> getEntityClazz() {

        return entity;
    }

    public Class<? extends Mount> getClazz() {

        return clazz;
    }

    public <T extends Mount> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T) Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

        }
        return t;
    }

    public static MountType fromType(String type) {

        switch (type.toLowerCase()) {

            case "mule":
                return MULE;
            case "donkey":
                return DONKEY;
            case "horse":
                return HORSE;
            case "zombiehorse":
            case "zombie_horse":
                return ZOMBIE_HORSE;
            case "skeletonhorse":
            case "skeleton_horse":
                return SKELETON_HORSE;
            default:
                return null;
        }
    }
}
