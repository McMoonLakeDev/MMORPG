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
 
  
package com.minecraft.moonlake.mmorpg.handle;

/**
 * Created by MoonLake on 2016/6/14.
 */
public enum EnumPlayerValueType {

    /**
     * 玩家值类型: 生命值
     */
    HEALTH("Health", "生命值"),
    /**
     * 玩家值类型: 魔法值
     */
    MAGIC("Magic", "魔法值"),
    ;

    private String type;
    private String name;

    EnumPlayerValueType(String type, String name) {

        this.type = type;
        this.name = name;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }
}
