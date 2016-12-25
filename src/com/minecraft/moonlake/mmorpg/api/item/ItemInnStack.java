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
 
  
package com.minecraft.moonlake.mmorpg.api.item;

import com.minecraft.moonlake.api.itemlib.ItemBuilder;
import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.handle.EnumAddItemStackCallBack;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/13.
 */
public class ItemInnStack implements ItemInn {

    private ItemStack item;
    private final static MMORPG main;

    static {

        main = MMORPGPlugin.getInstances();
    }

    public ItemInnStack(ItemStack item) {

        this.item = item;
    }

    public ItemInnStack(Material type, int data) {

        this(type, data, 1);
    }

    public ItemInnStack(Material type, int data, int amount) {

        this.item = new ItemBuilder(type, data).setAmount(amount).build();
    }

    public ItemInnStack(Material type, int data, int amount, String name) {

        this.item = new ItemBuilder(type, data, name).setAmount(amount).build();
    }

    public ItemInnStack(Material type, int data, int amount, String name, String... lore) {

        this.item = new ItemBuilder(type, data, name).setAmount(amount).addLores(lore).build();
    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    @Override
    public MMORPG getInstance() {

        return main;
    }

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    @Override
    public ItemStack getItem() {

        return item;
    }

    /**
     * 获取物品栈的数量
     *
     * @return 数量
     */
    @Override
    public int getAmount() {

        return item.getAmount();
    }

    /**
     * 复制 ItemInn 物品栈对象
     *
     * @return 复制的对象
     */
    @Override
    public ItemInn clone() {

        return new ItemInnStack(item);
    }

    @Override
    public boolean equals(Object obj) {

        if(obj != null) {

            if(obj instanceof ItemInn) {

                ItemInn itemInn = (ItemInn)obj;

                return ItemManager.compareAll(this.getItem(), itemInn.getItem());
            }
        }
        return false;
    }

    /**
     * 将货币物品栈自然掉落在指定位置
     *
     * @param location 位置
     * @return 物品实体对象 没有则返回 null
     */
    public Item dropItem(Location location) {

        return dropItem(location, false);
    }

    /**
     * 将物品栈自然掉落在指定位置
     *
     * @param location 位置
     * @param flag     是否显示物品栈自定义名称
     * @return 物品实体对象 没有则返回 null
     */
    @Override
    public Item dropItem(Location location, boolean flag) {

        if(location != null) {

            return EntityManager.dropItem(location, getItem(), flag);
        }
        return null;
    }

    /**
     * 将货币物品栈自然掉落在指定位置
     *
     * @param worldName 世界名
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @return 物品实体对象 没有则返回 null
     */
    public Item dropItem(String worldName, double x, double y, double z) {

        World world = Bukkit.getServer().getWorld(worldName);

        return dropItem(world, x, y, z);
    }

    /**
     * 将货币物品栈自然掉落在指定位置
     *
     * @param world 世界
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @return 物品实体对象 没有则返回 null
     */
    public Item dropItem(World world, double x, double y, double z) {

        if(world != null) {

            return dropItem(new Location(world, x, y, z));
        }
        return null;
    }

    /**
     * 将物品栈给予指定玩家背包
     *
     * @param mmorpgPlayer 玩家
     */
    public void givePlayer(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer != null) {

            mmorpgPlayer.addItemStack(EnumAddItemStackCallBack.DROP_LOCATION, getItem());
        }
    }

    /**
     * 获取物品栈类型
     *
     * @return 物品栈类型
     */
    @Override
    public Material getMaterial() {

        return getItem().getType();
    }
}
