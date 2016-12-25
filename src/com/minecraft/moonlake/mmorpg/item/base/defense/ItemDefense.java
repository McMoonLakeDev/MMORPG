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
 
  
package com.minecraft.moonlake.mmorpg.item.base.defense;

import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.mmorpg.item.base.BaseItem;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/12.
 */
public interface ItemDefense extends BaseItem {

    /**
     * 获取防御力
     *
     * @return 防御力
     */
    int getDefense();

    /**
     * 设置防御力
     *
     * @param defense 防御力
     */
    void setDefense(int defense);

    /**
     * 设置物品栈的攻击伤害属性
     *
     * @param item 物品栈
     */
    void setItemDefense(ItemStack item);

    /**
     * 获取属性的文本标签
     *
     * @return 标签
     */
    String getLore();

    /**
     * 物品防御值类型
     *
     * @return 防御值类型
     */
    ItemDefenseType getType();

    /**
     * 获取物品防御对应的槽位
     *
     * @return 槽位
     */
    Itemlib.AttributeType.Slot getSlot();

    /**
     * 设置物品防御对应的槽位
     *
     * @param slot 槽位
     */
    void setSlot(Itemlib.AttributeType.Slot slot);
}
