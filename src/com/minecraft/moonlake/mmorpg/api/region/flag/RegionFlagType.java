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
 
  
package com.minecraft.moonlake.mmorpg.api.region.flag;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.region.flag.type.RegionFlagLevel;
import com.minecraft.moonlake.mmorpg.api.region.flag.type.RegionFlagPermission;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/28.
 */
public enum RegionFlagType {

    /**
     * 区域标示类型: 等级
     */
    LEVEL("Level", RegionFlagLevel.class),
    /**
     * 区域标示类型: 权限
     */
    PERMISSION("Permission", RegionFlagPermission.class),;

    private String type;
    private Class<? extends RegionFlag> clazz;
    private final static Map<String, RegionFlagType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(RegionFlagType regionFlagType : values()) {

            NAME_MAP.put(regionFlagType.type.toLowerCase(), regionFlagType);
        }
    }

    RegionFlagType(String type, Class<? extends RegionFlag> clazz) {

        this.type = type;
        this.clazz = clazz;
    }

    /**
     * 获取区域标示的类型名
     *
     * @return 类型名
     */
    public String getName() {

        return type;
    }

    public Class<? extends RegionFlag> getClazz() {

        return clazz;
    }

    public <T extends RegionFlag> T newInstance() {

        T regionFlag = null;

        try {

            regionFlag = (T)getClazz().newInstance();
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("实例化区域标示对象时异常: " + e.getMessage());
        }
        return regionFlag;
    }

    public static RegionFlagType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
}
