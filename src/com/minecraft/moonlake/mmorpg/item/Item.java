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
 
  
package com.minecraft.moonlake.mmorpg.item;

import com.minecraft.moonlake.mmorpg.api.MMORPGCore;
import com.minecraft.moonlake.mmorpg.item.attribute.ItemAttribute;
import com.minecraft.moonlake.mmorpg.item.base.attackdamage.ItemAttackDamage;
import com.minecraft.moonlake.mmorpg.item.base.attackdamage.ItemAttackDamageType;
import com.minecraft.moonlake.mmorpg.item.base.attackspeed.ItemAttackSpeed;
import com.minecraft.moonlake.mmorpg.item.base.attackspeed.ItemAttackSpeedType;
import com.minecraft.moonlake.mmorpg.item.base.defense.ItemDefense;
import com.minecraft.moonlake.mmorpg.item.base.defense.ItemDefenseType;
import com.minecraft.moonlake.mmorpg.item.quality.QualityType;
import com.minecraft.moonlake.mmorpg.item.require.ItemRequire;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by MoonLake on 2016/5/14.
 */
public interface Item extends MMORPGCore {

    /**
     * 设置物品栈的攻击伤害
     *
     * @param item 物品栈
     * @param damage 伤害
     */
    void setItemAttackDamage(ItemStack item, ItemAttackDamage damage);

    /**
     * 设置物品栈的防御力
     *
     * @param item 物品栈
     * @param defense 防御
     */
    void setItemDefense(ItemStack item, ItemDefense defense);

    /**
     * 设置物品栈的攻击速度
     *
     * @param item 物品栈
     * @param attackSpeed 攻击速度
     */
    void setItemAttackSpeed(ItemStack item, ItemAttackSpeed attackSpeed);

    /**
     * 获取物品栈是否拥有攻击速度属性
     *
     * @param item 物品栈
     * @return 物品攻击速度属性 没有则返回 null
     */
    ItemAttackSpeed hasAttackSpeed(ItemStack item);

    /**
     * 获取物品栈是否拥有攻击伤害属性
     *
     * @param item 物品栈
     * @return 物品攻击伤害属性 没有则返回 null
     */
    ItemAttackDamage hasAttackDamage(ItemStack item);

    /**
     * 获取物品栈是否拥有防御属性
     *
     * @param item 物品栈
     * @return 物品防御属性 没有则返回 null
     */
    ItemDefense hasDefense(ItemStack item);

    /**
     * 获取物品栈是否拥有特殊属性
     *
     * @param item 物品栈
     * @return 物品特殊属性集合 没有则返回 null
     */
    List<ItemAttribute> hasAttribute(ItemStack item);

    /**
     * 给物品栈设置品质类型
     *
     * @param item 物品栈
     * @param type 品质类型
     */
    void setItemQuality(ItemStack item, QualityType type);

    /**
     * 获取物品栈的品质类型
     *
     * @param item 物品栈
     * @return 物品的品质 没有则返回 null
     */
    QualityType hasQuality(ItemStack item);

    /**
     * 获取物品栈是否拥有特殊需求属性
     *
     * @param item 物品栈
     * @return 物品特殊需求属性集合 没有则返回 null
     */
    List<ItemRequire> hasRequire(ItemStack item);

    /**
     * 设置物品栈的特殊需求属性
     *
     * @param item 物品栈
     * @param requires 特殊需求
     */
    void setItemRequire(ItemStack item, ItemRequire... requires);

    /**
     * 将物品栈的数据正确的进行排序
     *
     * @param item 物品栈
     */
    void sort(ItemStack item);

    /**
     * 创建物品攻击力属性
     *
     * @param type 类型
     * @param <T> 物品攻击力对象
     * @return 物品攻击力对象
     */
    <T extends ItemAttackDamage> T createItemAttackDamage(ItemAttackDamageType type);

    /**
     * 创建物品攻击速度属性
     *
     * @param type 类型
     * @param <T> 物品攻击速度对象
     * @return 物品攻击速度对象
     */
    <T extends ItemAttackSpeed> T createItemAttackSpeed(ItemAttackSpeedType type);

    /**
     * 创建物品防御力属性
     *
     * @param type 类型
     * @param <T> 物品防御力对象
     * @return 物品防御力对象
     */
    <T extends ItemDefense> T createItemDefense(ItemDefenseType type);
}
