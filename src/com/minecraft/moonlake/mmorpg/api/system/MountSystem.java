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
 
  
package com.minecraft.moonlake.mmorpg.api.system;

import com.minecraft.moonlake.mmorpg.api.gui.player.MountGUI;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

import java.util.List;

/**
 * Created by MoonLake on 2016/5/23.
 */
public interface MountSystem {

    /**
     * 获取拥有者玩家对象
     *
     * @return 玩家
     */
    MMORPGPlayer getOwner();

    /**
     * 获取此玩家的坐骑 GUI 对象
     *
     * @return 坐骑 GUI
     */
    MountGUI getMountGUI();

    /**
     * 将此玩家打开坐骑系统 GUI
     */
    void open();

    /**
     * 获取此玩家的坐骑集合
     *
     * @return 坐骑集合
     */
    List<Mount> getMountList();

    /**
     * 获取此玩家的坐骑数量
     *
     * @return 数量
     */
    int getMountSize();

    /**
     * 获取此玩家的出站坐骑
     *
     * @return 出站坐骑 没有返回 null
     */
    Mount getMain();

    /**
     * 设置此玩家的出战坐骑
     *
     * @param slot 坐骑 GUI 索引
     */
    void setMain(int slot);

    /**
     * 获取此玩家的出战坐骑索引
     *
     * @return 索引
     */
    int getMainSlot();

    /**
     * 给予此玩家新的坐骑
     *
     * @param mount 坐骑
     */
    void giveMount(Mount mount);

    /**
     * 更新此玩家的坐骑从索引
     *
     * @param newMount 坐骑
     * @param slot 索引
     */
    void updateMountFromSlot(Mount newMount, int slot);

    /**
     * 读取此玩家的坐骑数据
     */
    void loadData();

    /**
     * 保存此玩家的坐骑数据
     */
    void saveData();
}
