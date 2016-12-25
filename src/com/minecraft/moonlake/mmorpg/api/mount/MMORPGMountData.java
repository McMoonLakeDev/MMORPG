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
 
  
package com.minecraft.moonlake.mmorpg.api.mount;

import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerMountData;

import java.util.List;

/**
 * Created by MoonLake on 2016/6/19.
 */
public interface MMORPGMountData {

    /**
     * 初始化指定玩家的 MMORPG 坐骑数据
     *
     * @param name 玩家名
     * @param defaultMounts 默认坐骑
     * @return 是否成功
     */
    boolean initPlayerMountData(String name, Mount... defaultMounts);

    /**
     * 读取指定玩家的 MMORPG 坐骑数据
     *
     * @param name 玩家名
     * @return 坐骑集合 异常或没有则返回空集合
     */
    List<Mount> loadPlayerMountData(String name);

    /**
     * 保存指定玩家的 MMORPG 坐骑数据
     *
     * @param name 玩家名
     * @param mountData 坐骑数据
     * @return 是否成功
     */
    boolean savePlayerMountData(String name, MMORPGPlayerMountData mountData);

    /**
     * 保存所有 MMORPG 玩家坐骑数组数据
     *
     * @param mountDatas 数组数据
     * @return 是否成功
     */
    boolean savePlayerMountDatas(MMORPGPlayerMountData[] mountDatas);
}
