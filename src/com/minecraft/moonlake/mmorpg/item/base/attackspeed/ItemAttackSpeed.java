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
 
  
package com.minecraft.moonlake.mmorpg.item.base.attackspeed;

import com.minecraft.moonlake.mmorpg.item.base.BaseItem;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/12.
 */
public interface ItemAttackSpeed extends BaseItem {

    /**
     * 获取攻击速度属性的类型
     *
     * @return 类型
     */
    ItemAttackSpeedType getType();

    /**
     * 设置物品栈的攻击速度属性
     *
     * @param item 物品栈
     */
    void setItemAttackSpeed(ItemStack item);
}
