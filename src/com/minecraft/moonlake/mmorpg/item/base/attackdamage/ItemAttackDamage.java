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
 
  
package com.minecraft.moonlake.mmorpg.item.base.attackdamage;

import com.minecraft.moonlake.mmorpg.item.base.BaseItem;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/12.
 */
public interface ItemAttackDamage extends BaseItem {

    /**
     * 获取攻击伤害
     *
     * @param <T> 伤害值
     * @return 伤害值
     */
    <T extends Number> T getDamage();

    /**
     * 设置攻击伤害
     *
     * @param damage 伤害值
     * @param <T> 伤害值
     */
    <T extends Number> void setDamage(T damage);

    /**
     * 获取攻击伤害标签
     *
     * @return 标签
     */
    String getLore();

    /**
     * 物品伤害值类型
     *
     * @return 伤害值类型
     */
    ItemAttackDamageType getType();

    /**
     * 将物品栈设置攻击伤害
     *
     * @param item 伤害
     */
    void setItemAttackDamage(ItemStack item);
}
