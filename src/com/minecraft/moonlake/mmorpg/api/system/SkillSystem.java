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

import com.minecraft.moonlake.mmorpg.api.gui.player.SkillGUI;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillCombo;
import com.minecraft.moonlake.mmorpg.api.skill.passive.Passive;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.Ultimate;

import java.util.Set;

/**
 * Created by MoonLake on 2016/5/23.
 */
public interface SkillSystem extends SkillCombo {

    /**
     * 获取此玩家的技能组合对象
     *
     * @return 技能组合
     */
    SkillCombo getSkillCombo();

    /**
     * 获取此玩家的技能列表
     *
     * @return 技能列表
     */
    Set<Skill> getSkillList();

    /**
     * 获取此玩家的天赋列表
     *
     * @return 天赋列表
     */
    Set<Talent> getTalentList();

    /**
     * 给玩家添加技能
     *
     * @param skill 技能
     */
    @Override
    void addSkill(Skill skill);

    /**
     * 给玩家删除技能
     *
     * @param skill 技能
     */
    @Override
    void delSkill(Skill skill);

    /**
     * 给此玩家添加天赋技能
     *
     * @param talent 天赋
     */
    void addTalent(Talent talent);

    /**
     * 删除此玩家的天赋技能
     *
     * @param talent
     */
    void delTalent(Talent talent);

    /**
     * 清除此玩家的技能
     */
    void clearSkill();

    /**
     * 清除此玩家的天赋
     */
    void clearTalent();

    /**
     * 获取玩家的技能点数
     *
     * @return 技能点数
     */
    int getSkillPoint();

    /**
     * 设置玩家的技能点数
     *
     */
    void setSkillPoint(int point);

    /**
     * 给予指定玩家数量的技能点
     *
     * @param give 给予的数量
     */
    void giveSkillPoint(int give);

    /**
     * 减少指定玩家数量的技能点
     *
     * @param take 减少的数量
     */
    void takeSkillPoint(int take);

    /**
     * 获取玩家的被动技能
     *
     * @return 被动技能
     */
    Passive getPassive();

    /**
     * 设置玩家的被动技能
     *
     * @param passive 被动技能
     */
    void setPassive(Passive passive);

    /**
     * 获取玩家的技能大招
     *
     * @return 技能大招
     */
    Ultimate getUltimate();

    /**
     * 设置玩家的技能大招
     *
     * @param ultimate 技能大招
     */
    void setUltimate(Ultimate ultimate);

    /**
     * 获取玩家指定技能对象
     *
     * @param name 技能名
     * @return 技能对象 没有则返回 null
     */
    Skill getSkill(String name);

    /**
     * 获取玩家是否拥有指定技能
     *
     * @param name 技能名
     * @return true 则拥有此技能 else 没有
     */
    boolean hasSkill(String name);

    /**
     * 获取玩家是否拥有指定技能
     *
     * @param skill 技能对象
     * @return true 则拥有此技能 else 没有
     */
    boolean hasSkill(Skill skill);

    /**
     * 获取玩家的技能 GUI 对象
     *
     * @return GUI 对象
     */
    SkillGUI getSkillGUI();
}
