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
 
  
package com.minecraft.moonlake.mmorpg.api.gui;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.gui.api.GUI;
import com.minecraft.moonlake.gui.api.button.GUIButton;
import com.minecraft.moonlake.gui.api.button.GUIButtonExecute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/7/26.
 */
public interface MMORPGGUI extends GUI {

    /**
     * 获取此 GUI 对象的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取此 GUI 对象的标题名称
     *
     * @return 标题名称
     */
    String getTitle();

    /**
     * 获取此 GUI 对象的大小
     *
     * @return 大小
     */
    int getSize();

    /**
     * 设置指定索引为按钮对象
     *
     * @param slot 索引
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 的按钮对象
     */
    GUIButton setButton(int slot);

    /**
     * 设置指定二维坐标为按钮对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 按钮对象
     */
    GUIButton setButton(int x, int y);

    /**
     * 设置指定索引为物品对象
     *
     * @param slot 索引
     * @param icon 图标
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUIButtonConflictException 如果此索引已经为按钮则抛出异常
     */
    void setItem(int slot, ItemStack icon);

    /**
     * 设置指定二维坐标为按钮对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param icon 图标
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 按钮对象
     */
    GUIButton setButton(int x, int y, ItemStack icon);

    /**
     * 设置指定索引为按钮对象
     *
     * @param slot 索引
     * @param icon 图标
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 的按钮对象
     */
    GUIButton setButton(int slot, ItemStack icon);

    /**
     * 设置指定索引为按钮对象
     *
     * @param slot 索引
     * @param icon 图标
     * @param execute 执行
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 的按钮对象
     */
    GUIButton setButton(int slot, ItemStack icon, GUIButtonExecute execute);

    /**
     * 设置指定索引为按钮对象
     *
     * @param slot 索引
     * @param icon 图标
     * @param execute 执行
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 的按钮对象
     */
    GUIButton setButton(int slot, ItemStack icon, MMORPGGUIButtonExecute execute);

    /**
     * 设置指定二维坐标为按钮对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param icon 图标
     * @param execute 执行
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 按钮对象
     */
    GUIButton setButton(int x, int y, ItemStack icon, GUIButtonExecute execute);

    /**
     * 设置指定二维坐标为按钮对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param icon 图标
     * @param execute 执行
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     * @return GUI 按钮对象
     */
    GUIButton setButton(int x, int y, ItemStack icon, MMORPGGUIButtonExecute execute);

    /**
     * 将此 GUI 添加按钮对象
     *
     * @param icon 图标
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUIButtonOverflowException 如果无法再添加按钮则抛出异常
     * @return GUI 按钮对象 异常则返回 null
     */
    GUIButton addButton(ItemStack icon);

    /**
     * 将此 GUI 添加按钮对象
     *
     * @param icon 图标
     * @param execute 执行
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUIButtonOverflowException 如果无法再添加按钮则抛出异常
     * @return GUI 按钮对象 异常则返回 null
     */
    GUIButton addButton(ItemStack icon, GUIButtonExecute execute);

    /**
     * 将此 GUI 添加按钮对象
     *
     * @param icon 图标
     * @param execute 执行
     * @throws com.minecraft.moonlake.gui.exception.IllegalGUIButtonOverflowException 如果无法再添加按钮则抛出异常
     * @return GUI 按钮对象 异常则返回 null
     */
    GUIButton addButton(ItemStack icon, MMORPGGUIButtonExecute execute);

    /**
     * 获取指定索引是否为按钮对象
     *
     * @param slot 索引
     * @return true 则为按钮 else 不是
     */
    boolean isButton(int slot);

    /**
     * 获取指定二维坐标是否为按钮对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @return
     */
    boolean isButton(int x, int y);

    /**
     * 获取指定索引的按钮对象
     *
     * @param slot 索引
     * @return 按钮对象 异常或没有则返回 null
     */
    GUIButton getButton(int slot);

    /**
     * 获取指定二维坐标的按钮对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @return 按钮对象 异常或没有则返回 null
     */
    GUIButton getButton(int x, int y);

    /**
     * 清除指定索引的物品对象
     *
     * @param slot 索引
     */
    void removeItem(int slot);

    /**
     * 清除指定二维坐标的物品对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     */
    void removeItem(int x, int y);

    /**
     * 清除此 GUI 的所有物品和按钮对象
     */
    void clearAll();

    /**
     * 清除指定索引的按钮对象
     *
     * @param slot 索引
     * @return 按钮对象 异常没有则返回 null
     */
    GUIButton removeButton(int slot);

    /**
     * 清除指定二维坐标的按钮对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @return 按钮对象 异常没有则返回 null
     */
    GUIButton removeButton(int x, int y);

    /**
     * 清除此 GUI 的所有物品对象
     */
    void clearItems();

    /**
     * 清除此 GUI 的所有按钮对象
     */
    void clearButtons();

    /**
     * 将此 GUI 对象打开给指定玩家
     *
     * @param player 玩家
     */
    void open(Player player);

    /**
     * 将此 GUI 对象打开给指定玩家
     *
     * @param player 玩家
     */
    void open(MoonLakePlayer player);

    /**
     * 获取此 GUI 对象是否允许移动物品
     *
     * @return true 则允许 else 不允许
     */
    boolean isAllowMove();

    /**
     * 设置此 GUI 对象是否允许移动物品
     *
     * @param flag 是否允许
     */
    void setAllowMove(boolean flag);
}
