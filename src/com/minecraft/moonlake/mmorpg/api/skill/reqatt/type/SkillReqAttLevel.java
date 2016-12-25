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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.reqatt.type;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.AbstractSkillReqAtt;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAttType;
import com.minecraft.moonlake.mmorpg.manager.l18n;

/**
 * Created by MoonLake on 2016/7/7.
 */
public class SkillReqAttLevel extends AbstractSkillReqAtt {

    private int level;

    public SkillReqAttLevel() {

        this(0);
    }

    public SkillReqAttLevel(int level) {

        super(SkillReqAttType.LEVEL);

        this.level = level;
    }

    /**
     * 设置技能需求等级的等级值
     *
     * @param level 等级
     */
    public void setLevel(int level) {

        this.level = level;
    }

    /**
     * 获取技能需求等级的等级值
     *
     * @return 等级
     */
    public int getLevel() {

        return level;
    }

    /**
     * 检测玩家的技能需求属性
     *
     * @param mmorpgPlayer 玩家
     * @return true 则需求通过 else 不通过
     */
    @Override
    public boolean checkPlayer(MMORPGPlayer mmorpgPlayer) {

        return mmorpgPlayer.getLevel() > level;
    }

    /**
     * 获取格式化的技能需求属性
     *
     * @return 格式化
     */
    @Override
    public String formatSkillReqAtt() {

        return l18n.$("player.skill.reqAtt.description.level", level);
    }
}
