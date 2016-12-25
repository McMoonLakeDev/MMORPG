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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook;

import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.type.PointTpBook;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.type.TownTpBook;
import com.minecraft.moonlake.mmorpg.handle.EnumTpBookValueType;
import com.minecraft.moonlake.reflect.Reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/15.
 */
public enum TpBookType {

    POINT_TP_BOOK("Point", "位置传送书", EnumTpBookValueType.LOCATION, PointTpBook.class),
    TOWN_TP_BOOK("Town", "城镇传送书", EnumTpBookValueType.TOWN_LOCATION, TownTpBook.class),;

    private String type;
    private String name;
    private EnumTpBookValueType valueType;
    private Class<? extends TpBook> clazz;
    private final static Map<String, TpBookType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(TpBookType tpBookType : values()) {

            NAME_MAP.put(tpBookType.type.toLowerCase(), tpBookType);
        }
    }

    TpBookType(String type, String name, EnumTpBookValueType valueType, Class<? extends TpBook> clazz) {

        this.type = type;
        this.name = name;
        this.valueType = valueType;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public EnumTpBookValueType getValueType() {

        return valueType;
    }

    public Class<? extends TpBook> getClazz() {

        return clazz;
    }

    public <T extends TpBook> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T)Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

        }
        return t;
    }

    public static TpBookType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
}
