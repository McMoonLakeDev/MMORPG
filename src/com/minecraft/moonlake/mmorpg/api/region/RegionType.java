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
 
  
package com.minecraft.moonlake.mmorpg.api.region;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.region.copy.CopyRegionUtil;
import com.minecraft.moonlake.mmorpg.api.region.mob.MobRegionUtil;
import com.minecraft.moonlake.mmorpg.api.region.town.TownRegionUtil;
import com.minecraft.moonlake.reflect.Reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/27.
 */
public enum RegionType {

    /**
     * 区域类型: 城镇区域
     */
    TOWN("Town", TownRegionUtil.class),
    /**
     * 区域类型: 副本类型
     */
    COPY("Copy", CopyRegionUtil.class),
    /**
     * 区域类型: 怪物区域
     */
    MOB("Mob", MobRegionUtil.class)
    ;

    private String type;
    private Class<? extends Region> clazz;
    private final static Map<String, RegionType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(RegionType regionType : values()) {

            NAME_MAP.put(regionType.type.toLowerCase(), regionType);
        }
    }

    RegionType(String type, Class<? extends Region> clazz) {

        this.type = type;
        this.clazz = clazz;
    }

    public String getName() {

        return type;
    }

    public Class<? extends Region> getClazz() {

        return clazz;
    }

    public Region newInstance(String name) {

        Region region = null;

        try {

            region = (Region) Reflect.instantiateObject(getClazz(), name);
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("获取区域类型实例对象时异常: " + e.getMessage());
        }
        return region;
    }

    public static RegionType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
}
