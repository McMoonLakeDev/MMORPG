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
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.AbstractSkillReqAtt;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAttType;
import com.minecraft.moonlake.mmorpg.manager.l18n;

/**
 * Created by MoonLake on 2016/7/7.
 */
public class SkillReqAttDepend extends AbstractSkillReqAtt {

    private Skill depend;

    public SkillReqAttDepend() {

        this(null);
    }

    public SkillReqAttDepend(Skill depend) {

        this(depend, depend.getLevel());
    }

    public SkillReqAttDepend(Skill depend, int dependLevel) {

        super(SkillReqAttType.DEPEND);

        this.depend = depend;
        this.depend.setLevel(dependLevel);
    }

    /**
     * 设置此技能需求属性的技能依赖
     *
     * @param depend 技能依赖
     */
    public void setDepend(Skill depend) {

        this.depend = depend;
    }

    /**
     * 获取此技能需求属性的技能依赖
     *
     * @return 技能依赖
     */
    public Skill getDepend() {

        return depend;
    }

    /**
     * 检测玩家的技能需求属性
     *
     * @param mmorpgPlayer 玩家
     * @return true 则需求通过 else 不通过
     */
    @Override
    public boolean checkPlayer(MMORPGPlayer mmorpgPlayer) {

        if(depend == null) {

            return true;
        }
        Skill dependTarget = mmorpgPlayer.getSkill().getSkill(depend.getName());

        if(dependTarget != null && dependTarget.getLevel() >= depend.getLevel()) {

            return true;
        }
        return false;
    }

    /**
     * 获取格式化的技能需求属性
     *
     * @return 格式化
     */
    @Override
    public String formatSkillReqAtt() {

        if(depend == null) {

            return "";
        }
        return l18n.$("player.skill.reqAtt.description.depend", depend.getName(), depend.getLevel());
    }
}
