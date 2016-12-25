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

import com.minecraft.moonlake.mmorpg.api.MMORPGCore;

/**
 * Created by MoonLake on 2016/5/14.
 */
public interface ItemAttribute extends MMORPGCore {

    /**
     * 获取属性的文本标签
     *
     * @return 标签
     */
    String getLore();

    /**
     * 获取特殊属性类型
     *
     * @return 属性类型
     */
    AttributeType getAttributeType();
}
