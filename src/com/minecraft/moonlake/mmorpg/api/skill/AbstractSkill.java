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

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAtt;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAttType;
import com.minecraft.moonlake.mmorpg.manager.SkillComboManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/5/17.
 */
public abstract class AbstractSkill implements Skill {

    private final static MMORPG MAIN;

    private String name;
    private String displayName;
    private String description;
    private int combo;
    private int magic;
    private int level;
    private final List<SkillReqAtt> skillReqAttList;

    static {

        MAIN = MMORPGPlugin.getInstances();
    }

    public AbstractSkill(String name) {

        this.combo = 0;
        this.level = 0;
        this.name = name;
        this.displayName = name;
        this.skillReqAttList = new ArrayList<>();
    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    public MMORPG getInstance() {

        return MAIN;
    }

    /**
     * 获取技能的名称
     *
     * @return 名称
     */
    public String getName() {

        return name;
    }

    /**
     * 获取技能的显示名
     *
     * @return 显示名称
     */
    public String getDisplayName() {

        return displayName;
    }

    /**
     * 设置技能的显示名
     *
     * @param name 显示名称
     */
    public void setDisplayName(String name) {

        this.displayName = name;
    }

    /**
     * 设置技能的组合 ID
     *
     * @param combo 组合 ID
     */
    public void setCombo(int combo) {

        this.combo = combo;
    }

    /**
     * 设置技能的组合类型
     *
     * @param combos 组合类型
     */
    public void setCombo(SkillComboType[] combos) {

        this.combo = SkillComboManager.convertCombo(combos);
    }

    /**
     * 获取技能的组合 ID
     *
     * @return 组合 ID
     */
    public int getCombo() {

        return combo;
    }

    /**
     * 获取技能是否拥有组合 ID
     *
     * @return 组合 ID
     */
    public boolean hasCombo() {

        return getCombo() > 0;
    }

    /**
     * 获取技能消耗的魔法值
     *
     * @return 魔法
     */
    public int getMagic() {

        return magic;
    }

    /**
     * 设置技能消耗的魔法值
     *
     * @param magic 魔法值
     */
    public void setMagic(int magic) {

        this.magic = magic;
    }

    /**
     * 获取技能的描述
     *
     * @return 描述
     */
    public String getDescription() {

        return StringUtil.color(description);
    }

    /**
     * 设置技能的描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * 获取技能的等级
     *
     * @return 等级
     */
    public int getLevel() {

        return level;
    }

    /**
     * 设置技能的等级
     *
     * @param level 等级
     */
    public void setLevel(int level) {

        this.level = level;
    }

    /**
     * 将技能增加指定等级
     *
     * @param level 等级
     */
    @Override
    public void addLevel(int level) {

        this.level += level;
    }

    /**
     * 将技能减少指定等级
     *
     * @param level 等级
     */
    @Override
    public void subtractLevel(int level) {

        this.level -= level;

        if(this.level < 0) {

            setLevel(0);
        }
    }

    /**
     * 获取此技能是否有需求属性
     *
     * @return true 则有需求属性 else 没有
     */
    @Override
    public boolean hasReqAtt() {

        return skillReqAttList.size() > 0;
    }

    /**
     * 添加技能的需求属性值
     *
     * @param skillReqAtt 技能需求属性
     */
    @Override
    public void addReqAtt(SkillReqAtt skillReqAtt) {

        if(skillReqAtt != null) {

            this.skillReqAttList.add(skillReqAtt);
        }
    }

    /**
     * 删除指定技能需求属性类型
     *
     * @param skillReqAttType 技能需求属性类型
     */
    @Override
    public void removeReqAtt(SkillReqAttType skillReqAttType) {

        for(SkillReqAtt skillReqAtt : skillReqAttList) {

            if(skillReqAtt.getType() == skillReqAttType) {

                skillReqAttList.remove(skillReqAtt);
            }
        }
    }

    /**
     * 清除技能的需求属性值
     */
    @Override
    public void clearReqAtt() {

        this.skillReqAttList.clear();
    }

    /**
     * 获取技能的需求属性集合
     *
     * @return 需求属性集合
     */
    @Override
    public Set<SkillReqAtt> getReqAttList() {

        return new HashSet<>(skillReqAttList);
    }

    /**
     * 将技能的需求属性进行格式化
     *
     * @return 格式化字符串
     */
    @Override
    public String getFormatReqAtt() {

        if(hasReqAtt()) {

            String format = "";

            for(SkillReqAtt skillReqAtt : skillReqAttList) {

                format += skillReqAtt.formatSkillReqAtt() + ",";
            }
            return format.substring(0, format.lastIndexOf(","));
        }
        return "无";
    }

    /**
     * 释放此技能
     *
     * @param owner 释放者
     */
    public abstract void cast(MMORPGPlayer owner);

    @Override
    public int compareTo(Skill o) {

        return getName().compareTo(o.getName());
    }
}
