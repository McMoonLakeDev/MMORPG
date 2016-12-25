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

import com.minecraft.moonlake.gui.api.button.GUIButton;
import com.minecraft.moonlake.gui.api.button.GUIButtonExecute;
import com.minecraft.moonlake.gui.exception.IllegalGUIButtonOverflowException;
import com.minecraft.moonlake.gui.exception.IllegalGUISlotOutBoundException;
import com.minecraft.moonlake.gui.util.GUIReference;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/7/26.
 */
public class MMORPGGUIUtil extends GUIReference implements MMORPGGUI {

    public MMORPGGUIUtil(String name, String title, int size) {

        super("MMORPG:" + name, title, size);
    }

    /**
     * 设置指定索引为按钮对象
     *
     * @param slot    索引
     * @param icon    图标
     * @param execute 执行
     * @return GUI 的按钮对象
     * @throws IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     */
    @Override
    public GUIButton setButton(int slot, ItemStack icon, MMORPGGUIButtonExecute execute) {

        return setButton(slot, icon, (GUIButtonExecute) execute);
    }

    /**
     * 设置指定二维坐标为按钮对象
     *
     * @param x       X 坐标
     * @param y       Y 坐标
     * @param icon    图标
     * @param execute 执行
     * @return GUI 按钮对象
     * @throws IllegalGUISlotOutBoundException 如果索引越界超出大小则抛出异常
     */
    @Override
    public GUIButton setButton(int x, int y, ItemStack icon, MMORPGGUIButtonExecute execute) {

        return setButton(x, y, icon, (GUIButtonExecute) execute);
    }

    /**
     * 将此 GUI 添加按钮对象
     *
     * @param icon    图标
     * @param execute 执行
     * @return GUI 按钮对象 异常则返回 null
     * @throws IllegalGUIButtonOverflowException 如果无法再添加按钮则抛出异常
     */
    @Override
    public GUIButton addButton(ItemStack icon, MMORPGGUIButtonExecute execute) {

        return addButton(icon, (GUIButtonExecute) execute);
    }
}
