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
 
  
package com.minecraft.moonlake.mmorpg.api.data;

import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGInventoryData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerData;

import java.util.Map;

/**
 * Created by MoonLake on 2016/5/20.
 */
public interface MMORPGData {

    /**
     * 初始化指定玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @return 是否成功
     */
    boolean initData(String name);

    /**
     * 初始化指定玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @param maxHealth 最大血量
     * @param maxMagic 最大魔法
     * @param maxSoul 最大灵魂
     * @return 是否成功
     */
    boolean initData(String name, double maxHealth, int maxMagic, int maxSoul);

    /**
     * 获取指定玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @return 存在则返回数据 否则返回 null
     */
    MMORPGPlayerData getData(String name);

    /**
     * 保存指定玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @param data 数据对象
     * @return 是否成功
     */
    boolean saveData(String name, MMORPGPlayerData data);

    /**
     * 保存所有 MMORPG 玩家数组数据
     *
     * @param datas 数组数据
     * @return 是否成功
     */
    boolean saveDatas(MMORPGPlayerData[] datas);

    /**
     * 保存指定 MMORPG 玩家的背包数据
     *
     * @param name 玩家名
     * @param dataMap 序列化数据集合
     * @return 是否成功
     */
    boolean saveInventoryData(String name, Map<String, String> dataMap);

    /**
     * 保存所有 MMORPG 玩家背包数组数据
     *
     * @param inventoryDatas 数组数据
     * @return 是否成功
     */
    boolean saveInventoryDatas(MMORPGInventoryData[] inventoryDatas);

    /**
     * 读取指定 MMORPG 玩家的背包数据
     *
     * @param name 玩家名
     * @return 序列化数据集合 异常或没有返回空集合
     */
    Map<String, String> loadInventoryData(String name);

    /**
     * 保存指定 MMORPG 玩家的个人仓库数据
     *
     * @param name 玩家名
     * @param dataMap 序列化数据集合
     * @return 是否成功
     */
    boolean saveRepertoryData(String name, Map<Integer, String> dataMap);

    /**
     * 保存所有 MMORPG 玩家个人仓库数组数据
     *
     * @param repertoryDatas 数组数据
     * @return 是否成功
     */
    boolean saveRepertoryDatas(MMORPGInventoryData[] repertoryDatas);

    /**
     * 读取指定 MMORPG 玩家的个人仓库数据
     *
     * @param name 玩家名
     * @return 序列化数据集合 异常或没有则返回空集合
     */
    Map<Integer, String> loadRepertoryData(String name);
}
