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
 
  
package com.minecraft.moonlake.mmorpg.api.player.repertory;

import com.minecraft.moonlake.mmorpg.api.gui.PeepInventory;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.inventory.Inventory;

/**
 * Created by MoonLake on 2016/6/17.
 */
public interface Repertory extends PeepInventory {

    /**
     * 获取此个人仓库的拥有者
     *
     * @return 拥有者
     */
    MMORPGPlayer getOwner();

    /**
     * 将此玩家的个人仓库进行打开
     */
    void open();

    /**
     * 偷窥此物品栏
     *
     * @param peeped 偷窥者
     */
    @Override
    void peep(MMORPGPlayer peeped);

    /**
     * 获取此玩家的个人仓库标题
     *
     * @return 标题
     */
    String getTitle();

    /**
     * 获取玩家的个人仓库大小 (1-6)
     *
     * @return 仓库大小
     */
    int getSize();

    /**
     * 加载此玩家的个人仓库数据
     */
    void loadData();

    /**
     * 保存此玩家的个人仓库数据
     */
    void saveData();

    /**
     * 获取个人仓库的物品栏对象
     *
     * @return 物品栏对象
     */
    Inventory getInventory();
}
