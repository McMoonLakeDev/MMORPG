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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.reqatt;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/7/7.
 */
public interface SkillReqAtt {

    /**
     * 获取技能需求属性的类型
     *
     * @return 类型
     */
    SkillReqAttType getType();

    /**
     * 检测玩家的技能需求属性
     *
     * @param mmorpgPlayer 玩家
     * @return true 则需求通过 else 不通过
     */
    boolean checkPlayer(MMORPGPlayer mmorpgPlayer);

    /**
     * 获取格式化的技能需求属性
     *
     * @return 格式化
     */
    String formatSkillReqAtt();
}
