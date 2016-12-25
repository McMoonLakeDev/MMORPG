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
 
  
package com.minecraft.moonlake.mmorpg.effect;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.effect.type.Bleed;
import com.minecraft.moonlake.mmorpg.effect.type.Vertigo;
import com.minecraft.moonlake.reflect.Reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/9.
 */
public enum EffectType {

    /**
     * 效果类型: 出血
     */
    BLEED("Bleed", "出血", Bleed.class),
    /**
     * 效果类型: 眩晕
     */
    VERTIGO("Vertigo", "眩晕", Vertigo.class),
    ;

    private String type;
    private String name;
    private Class<? extends Effect> clazz;
    private final static Map<String, EffectType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(EffectType effectType : values()) {

            NAME_MAP.put(effectType.type.toLowerCase(), effectType);
        }
    }

    EffectType(String type, String name, Class<? extends Effect> clazz) {

        this.type = type;
        this.name = name;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public Class<? extends Effect> getClazz() {

        return clazz;
    }

    public <T extends Effect> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T)Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("实例化效果类实例时异常: " + e.getMessage());
        }
        return t;
    }

    public static EffectType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
}
