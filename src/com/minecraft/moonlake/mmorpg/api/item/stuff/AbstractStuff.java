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
 
  
package com.minecraft.moonlake.mmorpg.api.item.stuff;

import com.minecraft.moonlake.mmorpg.api.item.ItemInnStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/28.
 */
public abstract class AbstractStuff extends ItemInnStack implements Stuff {

    private final StuffType type;

    public AbstractStuff(StuffType type) {

        this(type, 1);
    }

    public AbstractStuff(StuffType type, int amount) {

        super(type.getMaterial(), type.getData(), amount, type.getName());

        this.type = type;
    }

    /**
     * 获取材料的类型
     *
     * @return 类型
     */
    @Override
    public StuffType getStuffType() {

        return type;
    }

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    @Override
    public ItemStack getItem() {

        return super.getItem();
    }
}
