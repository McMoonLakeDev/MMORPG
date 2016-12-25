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
 
  
package com.minecraft.moonlake.mmorpg.api.skill;

import com.minecraft.moonlake.mmorpg.api.skill.data.MMORPGPlayerSkillData;

/**
 * Created by MoonLake on 2016/6/1.
 */
public interface MMORPGSkillData {

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
