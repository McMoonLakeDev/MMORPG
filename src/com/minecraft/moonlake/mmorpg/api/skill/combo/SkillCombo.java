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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.combo;

import com.minecraft.moonlake.mmorpg.api.skill.Skill;

/**
 * Created by MoonLake on 2016/5/16.
 */
public interface SkillCombo {

    /**
     * 应用下次组合类型
     *
     * @param comboType 组合类型
     */
    void applyClick(SkillComboType comboType);

    /**
     * 清除全部组合
     */
    void clearCombo();

    /**
     * 获取当前组合的 ID
     *
     * @return 组合 ID
     */
    int getCurrentComboId();

    /**
     * 获取当前组合的字符串
     *
     * @return 组合字符串
     */
    String getCurrentComboString();

    /**
     * 给玩家添加技能
     *
     * @param skill 技能
     */
    void addSkill(Skill skill);

    /**
     * 给玩家删除技能
     *
     * @param skill 技能
     */
    void delSkill(Skill skill);

    /**
     * 设置玩家的技能组合 ID
     *
     * @param skill 技能
     * @param combo 组合 ID
     */
    void setSkill(Skill skill, int combo);

    /**
     * 设置玩家的技能组合类型
     *
     * @param skill 技能
     * @param combos 组合类型
     */
    void setSkill(Skill skill, SkillComboType[] combos);

    /**
     * 清除技能
     */
    void clearSkill();

    /**
     * 获取当前组合的索引
     *
     * @return 已经组合的索引
     */
    int getComboIndex();
}
