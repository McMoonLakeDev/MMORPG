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
 
  
package com.minecraft.moonlake.mmorpg.item.base;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;

/**
 * Created by MoonLake on 2016/5/16.
 */
public abstract class AbstractBaseItem implements BaseItem {

    private final BaseType type;
    private final static MMORPG main;

    static {

        main = MMORPGPlugin.getInstances();
    }

    @Override
    public MMORPG getInstance() {

        return main;
    }

    public AbstractBaseItem(BaseType type) {

        this.type = type;
    }

    /**
     * 获取基础物品属性类型
     *
     * @return 属性类型
     */
    public BaseType getBaseType() {

        return type;
    }
}
