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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.item.expendable.Expendable;
import com.minecraft.moonlake.mmorpg.api.item.expendable.ExpendableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/15.
 */
public interface TpBook extends Expendable {

    /**
     * 获取消耗品的类型
     *
     * @return 消耗品类型
     */
    ExpendableType getType();

    /**
     * 获取传送书的类型
     *
     * @return 传送书类型
     */
    TpBookType getTpBookType();

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    ItemStack getItem();

    /**
     * 获取物品栈的数量
     *
     * @return 数量
     */
    int getAmount();

    /**
     * 复制 TpBook 物品栈对象
     *
     * @return 复制的对象
     */
    TpBook clone();

    /**
     * 将消耗品传送书自然掉落在指定位置
     *
     * @param location 位置
     * @return 物品实体对象 没有则返回 null
     */
    org.bukkit.entity.Item dropItem(Location location);

    /**
     * 将物品栈自然掉落在指定位置
     *
     * @param location 位置
     * @param flag 是否显示物品栈自定义名称
     * @return 物品实体对象 没有则返回 null
     */
    org.bukkit.entity.Item dropItem(Location location, boolean flag);

    /**
     * 将消耗品物品栈自然掉落在指定位置
     *
     * @param worldName 世界名
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @return 物品实体对象 没有则返回 null
     */
    org.bukkit.entity.Item dropItem(String worldName, double x, double y, double z);

    /**
     * 将消耗品传送书自然掉落在指定位置
     *
     * @param world 世界
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @return 物品实体对象 没有则返回 null
     */
    org.bukkit.entity.Item dropItem(World world, double x, double y, double z);

    /**
     * 将物品栈传送书给予指定玩家背包
     *
     * @param mmorpgPlayer 玩家
     */
    void givePlayer(MMORPGPlayer mmorpgPlayer);

    /**
     * 将消耗品传送书使用给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    void useItem(MMORPGPlayer mmorpgPlayer);

    /**
     * 获取传送书的目标位置
     *
     * @return 目标位置
     */
    Location getLocation();

    /**
     * 获取消耗品传送书的物品栈类型
     *
     * @return 物品栈类型
     */
    Material getMaterial();
}
