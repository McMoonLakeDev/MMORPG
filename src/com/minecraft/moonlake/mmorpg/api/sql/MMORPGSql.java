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
 
  
package com.minecraft.moonlake.mmorpg.api.sql;

import com.minecraft.moonlake.mmorpg.api.data.MMORPGData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGInventoryData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerMountData;
import com.minecraft.moonlake.mmorpg.api.mount.MMORPGMountData;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.skill.MMORPGSkillData;
import com.minecraft.moonlake.mmorpg.api.skill.data.MMORPGPlayerSkillData;

import java.util.List;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/14.
 */
public interface MMORPGSql extends MMORPGData, MMORPGMountData, MMORPGSkillData {

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

    /**
     * 初始化玩家的技能以及天赋数据
     *
     * @param name 玩家名
     * @return 是否成功
     */
    boolean initPlayerSkillData(String name);

    /**
     * 读取玩家的技能以及天赋数据
     *
     * @param name 玩家名
     * @return 玩家技能数据对象 没有则返回 null
     */
    MMORPGPlayerSkillData loadPlayerSkillData(String name);

    /**
     * 保存玩家的技能以及天赋数据
     *
     * @param name 玩家名
     * @param skillData 玩家技能数据对象
     * @return 是否成功
     */
    boolean savePlayerSkillData(String name, MMORPGPlayerSkillData skillData);

    /**
     * 获取玩家的技能点数
     *
     * @param name 玩家名
     * @return 技能点数
     */
    int getPlayerSkillPoint(String name);

    /**
     * 设置玩家的技能点数
     *
     * @param name 玩家名
     * @param point 新的技能点数
     * @return 是否成功
     */
    boolean setPlayerSkillPoint(String name, int point);

    /**
     * 给予指定玩家数量的技能点
     *
     * @param name 玩家名
     * @param give 给予的数量
     * @return 是否成功
     */
    boolean givePlayerSkillPoint(String name, int give);

    /**
     * 减少指定玩家数量的技能点
     *
     * @param name 玩家名
     * @param take 减少的数量
     * @return 是否成功
     */
    boolean takePlayerSkillPoint(String name, int take);

    /**
     * 保存所有玩家的 MMORPG 技能数据到云
     *
     * @param skillDatas 技能数组集合
     * @return 是否成功
     */
    boolean savePlayerSkillDatas(MMORPGPlayerSkillData[] skillDatas);
}
