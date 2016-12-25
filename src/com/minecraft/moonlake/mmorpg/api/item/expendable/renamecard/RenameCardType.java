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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard;

import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.type.RenameCardMount;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.type.RenameCardPet;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.type.RenameCardPlayer;
import com.minecraft.moonlake.reflect.Reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/20.
 */
public enum RenameCardType {

    /**
     * 改名卡类型: 玩家改名卡
     */
    PLAYER_RENAME_CARD("Player", "玩家改名卡", "手持此物品并右击", RenameCardPlayer.class),
    /**
     * 改名卡类型: 坐骑改名卡
     */
    MOUNT_RENAME_CARD("Mount", "坐骑改名卡", "骑乘坐骑时手持此物品并右击", RenameCardMount.class),
    /**
     * 改名卡类型: 宠物改名卡
     */
    PET_RENAME_CARD("Pet", "宠物改名卡", "手持此物品右击自己宠物", RenameCardPet.class),
    ;

    private String type;
    private String name;
    private String method;
    private Class<? extends RenameCard> clazz;
    private final static Map<String, RenameCardType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(RenameCardType renameCardType : values()) {

            NAME_MAP.put(renameCardType.type.toLowerCase(), renameCardType);
        }
    }

    RenameCardType(String type, String name, String method, Class<? extends RenameCard> clazz) {

        this.type = type;
        this.name = name;
        this.method = method;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public String getMethod() {

        return method;
    }

    public Class<? extends RenameCard> getClazz() {

        return clazz;
    }

    public <T extends RenameCard> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T) Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

        }
        return t;
    }

    public static RenameCardType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
}
