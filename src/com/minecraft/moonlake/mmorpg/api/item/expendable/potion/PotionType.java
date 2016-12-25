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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.potion;

import com.minecraft.moonlake.mmorpg.handle.EnumPlayerValueType;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.type.HealthPotion;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.type.MagicPotion;
import com.minecraft.moonlake.reflect.Reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/14.
 */
public enum PotionType {

    /**
     * 药水类型: 生命药水
     */
    HEALTH_POTION("Health", "healing", "生命药水", 10, EnumPlayerValueType.HEALTH, HealthPotion.class),
    /**
     * 药水类型: 魔法药水
     */
    MAGIC_POTION("Magic", "night_vision", "魔法药水", 16, EnumPlayerValueType.MAGIC, MagicPotion.class),
    ;

    private String type;
    private String potion;
    private String name;
    private int id;
    private EnumPlayerValueType valueType;
    private Class<? extends Potion> clazz;
    private final static Map<String, PotionType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(PotionType potionType : values()) {

            NAME_MAP.put(potionType.type.toLowerCase(), potionType);
        }
    }

    PotionType(String type, String potion, String name, int id, EnumPlayerValueType valueType, Class<? extends Potion> clazz) {

        this.type = type;
        this.potion = potion;
        this.name = name;
        this.id = id;
        this.valueType = valueType;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getPotion() {

        return potion;
    }

    public String getName() {

        return name;
    }

    public int getId() {

        return id;
    }

    public EnumPlayerValueType getValueType() {

        return valueType;
    }

    public Class<? extends Potion> getClazz() {

        return clazz;
    }

    public <T extends Potion> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T)Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

        }
        return t;
    }

    public static PotionType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
}
