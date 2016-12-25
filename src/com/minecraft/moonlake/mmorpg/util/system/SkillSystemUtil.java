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
 
  
package com.minecraft.moonlake.mmorpg.util.system;

import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.gui.player.SkillGUI;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillCombo;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.api.skill.passive.Passive;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.Ultimate;
import com.minecraft.moonlake.mmorpg.api.system.SkillSystem;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.DataManager;
import com.minecraft.moonlake.mmorpg.util.skill.combo.SkillComboUtil;

import java.util.*;

/**
 * Created by MoonLake on 2016/5/23.
 */
public class SkillSystemUtil implements SkillSystem {

    private final MMORPGPlayer mmorpgPlayer;
    private Passive passive;
    private List<Skill> skillList;
    private List<Talent> talentList;
    private Ultimate ultimate;
    private SkillCombo skillCombo;

    public SkillSystemUtil(MMORPGPlayer mmorpgPlayer) {

        this.mmorpgPlayer = mmorpgPlayer;
        this.passive = null;
        this.skillList = new ArrayList<>();
        this.talentList = new ArrayList<>();
        this.skillCombo = new SkillComboUtil(mmorpgPlayer, ConfigManager.get("SkillCombo.ComboSize").asInt());
    }

    /**
     * 获取此玩家的技能组合对象
     *
     * @return 技能组合
     */
    @Override
    public SkillCombo getSkillCombo() {

        return skillCombo;
    }

    /**
     * 获取此玩家的技能列表
     *
     * @return 技能列表
     */
    @Override
    public Set<Skill> getSkillList() {

        return new TreeSet<>(skillList);
    }

    /**
     * 获取此玩家的天赋列表
     *
     * @return 天赋列表
     */
    @Override
    public Set<Talent> getTalentList() {

        return new TreeSet<>(talentList);
    }

    /**
     * 应用下次组合类型
     *
     * @param comboType 组合类型
     */
    @Override
    public void applyClick(SkillComboType comboType) {

        skillCombo.applyClick(comboType);
    }

    /**
     * 清除全部组合
     */
    @Override
    public void clearCombo() {

        skillCombo.clearCombo();
    }

    /**
     * 获取当前组合的 ID
     *
     * @return 组合 ID
     */
    @Override
    public int getCurrentComboId() {

        return skillCombo.getCurrentComboId();
    }

    /**
     * 获取当前组合的字符串
     *
     * @return 组合字符串
     */
    @Override
    public String getCurrentComboString() {

        return skillCombo.getCurrentComboString();
    }

    /**
     * 给玩家添加技能
     *
     * @param skill 技能
     */
    @Override
    public void addSkill(Skill skill) {

        skillList.add(skill);
        skillCombo.addSkill(skill);
    }

    /**
     * 给玩家删除技能
     *
     * @param skill 技能
     */
    @Override
    public void delSkill(Skill skill) {

        skillList.remove(skill);
        skillCombo.delSkill(skill);
    }

    /**
     * 给此玩家添加天赋技能
     *
     * @param talent 天赋
     */
    @Override
    public void addTalent(Talent talent) {

        talentList.add(talent);
    }

    /**
     * 删除此玩家的天赋技能
     *
     * @param talent
     */
    @Override
    public void delTalent(Talent talent) {

        talentList.remove(talent);
    }

    /**
     * 清除此玩家的技能
     */
    @Override
    public void clearSkill() {

        skillList.clear();
        skillCombo.clearSkill();
    }

    /**
     * 获取当前组合的索引
     *
     * @return 已经组合的索引
     */
    @Override
    public int getComboIndex() {

        return skillCombo.getComboIndex();
    }

    /**
     * 清除此玩家的天赋
     */
    @Override
    public void clearTalent() {

        talentList.clear();
    }

    /**
     * 获取玩家的技能点数
     *
     * @return 技能点数
     */
    @Override
    public int getSkillPoint() {

        return DataManager.getPlayerSkillPoint(mmorpgPlayer.getName());
    }

    /**
     * 设置玩家的技能点数
     *
     * @param point
     */
    @Override
    public void setSkillPoint(int point) {

        DataManager.setPlayerSkillPoint(mmorpgPlayer.getName(), point);
    }

    /**
     * 给予指定玩家数量的技能点
     *
     * @param give 给予的数量
     */
    @Override
    public void giveSkillPoint(int give) {

        DataManager.givePlayerSkillPoint(mmorpgPlayer.getName(), give);
    }

    /**
     * 减少指定玩家数量的技能点
     *
     * @param take 减少的数量
     */
    @Override
    public void takeSkillPoint(int take) {

        DataManager.takePlayerSkillPoint(mmorpgPlayer.getName(), take);
    }

    /**
     * 获取玩家的被动技能
     *
     * @return 被动技能
     */
    @Override
    public Passive getPassive() {

        return passive;
    }

    /**
     * 设置玩家的被动技能
     *
     * @param passive 被动技能
     */
    @Override
    public void setPassive(Passive passive) {

        this.passive = passive;
    }

    /**
     * 获取玩家的技能大招
     *
     * @return 技能大招
     */
    @Override
    public Ultimate getUltimate() {

        return ultimate;
    }

    /**
     * 设置玩家的技能大招
     *
     * @param ultimate 技能大招
     */
    @Override
    public void setUltimate(Ultimate ultimate) {

        this.ultimate = ultimate;
    }

    /**
     * 获取玩家指定技能对象
     *
     * @param name 技能名
     * @return 技能对象 没有则返回 null
     */
    @Override
    public Skill getSkill(String name) {

        Skill target = null;

        for(Skill skill : skillList) {

            if(skill != null && skill.getName().equals(name)) {

                target = skill;
            }
            if(target != null) {

                break;
            }
        }
        return target;
    }

    /**
     * 获取玩家是否拥有指定技能
     *
     * @param name 技能名
     * @return true 则拥有此技能 else 没有
     */
    @Override
    public boolean hasSkill(String name) {

        boolean result = false;

        for(Skill skill : skillList) {

            result = skill != null && skill.getName().equals(name);

            if(!result) {

                break;
            }
        }
        return result;
    }

    /**
     * 获取玩家是否拥有指定技能
     *
     * @param skill 技能对象
     * @return true 则拥有此技能 else 没有
     */
    @Override
    public boolean hasSkill(Skill skill) {

        return skill != null && hasSkill(skill.getName());
    }

    /**
     * 获取玩家的技能 GUI 对象
     *
     * @return GUI 对象
     */
    @Override
    public SkillGUI getSkillGUI() {

        return GUIManager.getSkillGUI(mmorpgPlayer);
    }

    /**
     * 设置玩家的技能组合 ID
     *
     * @param skill 技能
     * @param combo 组合 ID
     */
    @Override
    public void setSkill(Skill skill, int combo) {

        skillCombo.setSkill(skill, combo);
    }

    /**
     * 设置玩家的技能组合类型
     *
     * @param skill  技能
     * @param combos 组合类型
     */
    @Override
    public void setSkill(Skill skill, SkillComboType[] combos) {

        skillCombo.setSkill(skill, combos);
    }
}
