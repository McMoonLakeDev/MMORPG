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
 
  
package com.minecraft.moonlake.mmorpg.item.attribute;

/**
 * Created by MoonLake on 2016/5/14.
 */
public enum AttributeType {

    ;

    private String type;
    private String name;
    private Class<? extends AbstractAttribute> clazz;

    AttributeType(String type, String name, Class<? extends AbstractAttribute> clazz) {

        this.type = type;
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * 获取物品属性类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取物品属性中文名
     *
     * @return 中文名
     */
    public String getName() {

        return name;
    }

    /**
     * 获取特殊属性子类类对象
     *
     * @return 类对象
     */
    public Class<? extends AbstractAttribute> getClazz() {

        return clazz;
    }
}
