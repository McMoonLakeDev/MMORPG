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

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;

/**
 * Created by MoonLake on 2016/5/14.
 */
public abstract class AbstractAttribute implements ItemAttribute {

    private final AttributeType type;

    public AbstractAttribute(AttributeType type) {

        this.type = type;
    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    public MMORPG getInstance() {

        return MMORPGPlugin.getInstances();
    }

    /**
     * 获取特殊属性类型
     *
     * @return 属性类型
     */
    public AttributeType getAttributeType() {

        return type;
    }

    /**
     * 获取属性的文本标签
     *
     * @return 标签
     */
    public abstract String getLore();

    public enum AttributeValueType {

        /**
         * 普通值类型
         */
        NORMAL,
        /**
         * 真实值类型
         */
        REAL,
        /**
         * 范围值类型
         */
        SCOPE,
        /**
         * 百分比值类型
         */
        PERCENT
        ;
    }
}
