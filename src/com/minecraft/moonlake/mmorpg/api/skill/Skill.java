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
import com.minecraft.moonlake.mmorpg.api.MMORPGCore;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAtt;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAttType;

import java.util.Set;

/**
 * Created by MoonLake on 2016/5/16.
 */
public interface Skill extends Comparable<Skill>, MMORPGCore {

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    MMORPG getInstance();

    /**
     * 获取技能的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取技能的显示名
     *
     * @return 显示名称
     */
    String getDisplayName();

    /**
     * 设置技能的显示名
     *
     * @param name 显示名称
     */
    void setDisplayName(String name);

    /**
     * 设置技能的组合 ID
     *
     * @param combo 组合 ID
     */
    void setCombo(int combo);

    /**
     * 设置技能的组合类型
     *
     * @param combos 组合类型
     */
    void setCombo(SkillComboType[] combos);

    /**
     * 获取技能的组合 ID
     *
     * @return 组合 ID
     */
    int getCombo();

    /**
     * 获取技能是否拥有组合 ID
     *
     * @return 组合 ID
     */
    boolean hasCombo();

    /**
     * 获取技能消耗的魔法值
     *
     * @return 魔法
     */
    int getMagic();

    /**
     * 设置技能消耗的魔法值
     *
     * @param magic 魔法值
     */
    void setMagic(int magic);

    /**
     * 获取技能的描述
     *
     * @return 描述
     */
    String getDescription();

    /**
     * 设置技能的描述
     *
     * @param description 描述
     */
    void setDescription(String description);

    /**
     * 获取技能的等级
     *
     * @return 等级
     */
    int getLevel();

    /**
     * 设置技能的等级
     *
     * @param level 等级
     */
    void setLevel(int level);

    /**
     * 将技能增加指定等级
     *
     * @param level 等级
     */
    void addLevel(int level);

    /**
     * 将技能减少指定等级
     *
     * @param level 等级
     */
    void subtractLevel(int level);

    /**
     * 获取此技能是否有需求属性
     *
     * @return true 则有需求属性 else 没有
     */
    boolean hasReqAtt();

    /**
     * 添加技能的需求属性值
     *
     * @param skillReqAtt 技能需求属性
     */
    void addReqAtt(SkillReqAtt skillReqAtt);

    /**
     * 删除指定技能需求属性类型
     *
     * @param skillReqAttType 技能需求属性类型
     */
    void removeReqAtt(SkillReqAttType skillReqAttType);

    /**
     * 清除技能的需求属性值
     */
    void clearReqAtt();

    /**
     * 获取技能的需求属性集合
     *
     * @return 需求属性集合
     */
    Set<SkillReqAtt> getReqAttList();

    /**
     * 将技能的需求属性进行格式化
     *
     * @return 格式化字符串
     */
    String getFormatReqAtt();

    /**
     * 释放此技能
     *
     * @param owner 释放者
     */
    void cast(MMORPGPlayer owner);

    int compareTo(Skill o);

    /**
     * 获取技能对象实例从类名
     */
    class fromName {

        /**
         * 从技能类名获取到技能的实例对象
         *
         * @param name 类名
         * @return 技能对象实例 异常返回 null
         */
        public static Skill a(String name) {

            Skill obj = null;

            try {

                obj = (Skill)MMORPGPlugin.getInstances().getClassLoader$().loadClass("com.minecraft.moonlake.mmorpg.api.skill.active." + name).newInstance();
            }
            catch (Exception e) {

                MMORPGPlugin.getInstances().log("实例化技能类对象时异常: " + e.getMessage());
            }
            return obj;
        }
    }
}
