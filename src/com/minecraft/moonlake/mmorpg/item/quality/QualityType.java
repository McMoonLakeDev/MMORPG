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
 
  
package com.minecraft.moonlake.mmorpg.item.quality;

/**
 * Created by MoonLake on 2016/5/14.
 */
public enum QualityType {

    /**
     * 物品品质类型: 白色
     */
    WHITE("White", "白色", "&f"),
    /**
     * 物品品质类型: 黄色
     */
    YELLOW("Yellow", "黄色", "&e"),
    /**
     * 物品品质类型: 粉色
     */
    PINK("Pink", "粉色", "&d"),
    /**
     * 物品品质类型: 蓝色
     */
    BLUE("Blue", "蓝色", "&9"),
    ;

    private String type;
    private String name;
    private String color;

    QualityType(String type, String name, String color) {

        this.type = type;
        this.name = name;
        this.color = color;
    }

    /**
     * 获取物品品质类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取物品品质中文名
     *
     * @return 中文名
     */
    public String getName() {

        return name;
    }

    /**
     * 获取物品品质的颜色
     *
     * @return 颜色
     */
    public String getColor() {

        return color;
    }
}
