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
 
  
package com.minecraft.moonlake.mmorpg.item.require.excel;

import com.minecraft.moonlake.mmorpg.item.require.AbstractRequire;
import com.minecraft.moonlake.mmorpg.item.require.RequireType;

/**
 * 物品特殊需求属性: 使用等级
 * Created by MoonLake on 2016/5/14.
 */
public class MinLevel extends AbstractRequire {

    private int minLvl;

    /**
     * 物品特殊需求属性: 使用等级
     * {@link com.minecraft.moonlake.mmorpg.api.item.require.RequireType}
     *
     */
    public MinLevel() {

        this(1);
    }

    /**
     * 获取物品特殊需求属性类型
     *
     * @return 属性类型
     */
    @Override
    public RequireType getType() {

        return RequireType.MIN_LEVEL;
    }

    /**
     * 获取物品特殊需求属性类型值
     *
     * @return 属性类型值
     */
    @Override
    public String getRequireValue() {

        return String.valueOf(minLvl);
    }

    /**
     * 物品特殊需求: 使用等级
     * {@link com.minecraft.moonlake.mmorpg.api.item.require.RequireType}
     *
     * @param minLevel 使用等级
     */
    public MinLevel(int minLevel) {

        this.minLvl = minLevel;
    }

    /**
     * 获取使用等级
     *
     * @return
     */
    public int getMinLevel() {

        return minLvl;
    }

    /**
     * 设置使用等级
     *
     * @param minLevel
     */
    public void setMinLevel(int minLevel) {

        this.minLvl = minLevel;
    }
}
